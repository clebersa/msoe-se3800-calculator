package edu.msoe.se.se3800.calculator;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.LinkedList;

public class Calculator {

    /**
     * This is a Linked list to store the history of equations. The index of the
     * equation corresponds to the index in the answer list
     */
    private final LinkedList<String> historyEquations;

    /**
     * This is a Linked list to store the answers to the equations in the
     * history. The index of the answer corresponds to the index in the equation
     * list
     */
    private final LinkedList<Double> historyAnswers;

    /**
     * Valid operations to perform calculations.
     */
    private final String[] VALID_OPERATIONS = {"add", "sub", "mul", "div"};

    /**
     * Pattern used to split the equation.
     */
    private final String PATTERN = " |\t";

    public Calculator() {
        historyEquations = new LinkedList<>();
        historyAnswers = new LinkedList<>();
    }

    /**
     * Solves an equation.
     *
     * @author cleber
     * @param equation Equation to be solved.
     * @return The result of the equation solving.
     * @throws InvalidParameterException if the equation contains some error of
     * syntax or if some error is found during the equation processing.
     */
    //All Use Stories, especially #8
    public double solveEquation(String equation) throws InvalidParameterException {
        double result;

        if (equation == null) {
            throw new InvalidParameterException("null equation");
        }

        equation = equation.trim();
        if (equation.isEmpty()) {
            throw new InvalidParameterException("empty equation");
        }

        if ("hist".equalsIgnoreCase(equation)) {
            displayHist();
            result = 0.0;
        } else if ("clear".equalsIgnoreCase(equation)) {
            clearHist();
            result = 0.0;
        } else {
            String errorMessage = checkSyntax(equation);
            if (!errorMessage.isEmpty()) {
                throw new InvalidParameterException(errorMessage);
            }
            result = processTerms(splitEquation(equation));
            historyAnswers.add(result);
            historyEquations.add(String.join(" ", equation.split(PATTERN)));
        }

        return result;
    }

    /**
     * Checks the syntax of an equation.
     *
     * @author cleber
     * @param equation The equation to be checked.
     * @return A empty string if the syntax has no problem. Otherwise, this
     * function returns a string containing an error message.
     */
    private String checkSyntax(String equation) {
        int parentController = 0;
        String elements[] = equation.split(PATTERN);
        String element;
        for (int elementCounter = 0; elementCounter < elements.length; elementCounter++) {
            element = elements[elementCounter];

            //Operation checking
            boolean beginParent = false;
            if (element.startsWith("(")) {
                beginParent = true;
                parentController++;
                element = element.substring(1);
            }
            boolean isOperation = false;
            for (String option : VALID_OPERATIONS) {
                if (option.equalsIgnoreCase(element)) {
                    isOperation = true;
                }
            }
            if (isOperation) {
                continue;
            } else if (beginParent) {
                return "invalid operation '" + element + "' after '('";
            } else if (elementCounter == 0) {
                return "invalid operation '" + element + "' at the beginning";
            }

            //Number checking
            if (element.startsWith("!")) {
                element = element.substring(1);
            }
            int firstEndParent = element.indexOf(")");
            if (firstEndParent == 0) {
                return "missing number before ')'";
            }

            String numberString;
            if (firstEndParent == -1) {
                numberString = element;
            } else {
                numberString = element.substring(0, firstEndParent);
                String rest = element.substring(firstEndParent, element.length());
                for (char character : rest.toCharArray()) {
                    if (character != ')') {
                        return "invalid element '" + element + "'";
                    } else if (parentController == 0) {
                        return "unexpected ')'";
                    }
                    parentController--;
                }
            }
            try {
                Double.parseDouble(numberString);
            } catch (NumberFormatException e) {
                return "invalid number '" + numberString + "'";
            }
        }

        if (parentController > 0) {
            return "unexpected '('";
        }

        return "";
    }

    /**
     * Splits and equation into an LinkedList to be processed.
     *
     * @author cleber
     * @param equation String containing the equation to be processed.
     * @return The LinkedList containing the equation split.
     */
    private LinkedList splitEquation(String equation) {
        LinkedList terms = new LinkedList();
        String line = equation;

        int indexFirstParent = line.indexOf("(");
        if (indexFirstParent == -1) {
            terms.addAll(Arrays.asList(line.split(PATTERN)));
        } else {
            String part = line.substring(0, indexFirstParent);
            terms.addAll(Arrays.asList(part.split(PATTERN)));
            line = line.substring(indexFirstParent);
            indexFirstParent = 0;
            do {
                int parentCounter = 0;
                int indexMatchParent;
                for (indexMatchParent = 0;
                        indexMatchParent < line.length(); indexMatchParent++) {
                    if (line.charAt(indexMatchParent) == ')') {
                        if (parentCounter == 1) {
                            break;
                        } else {
                            parentCounter--;
                        }
                    } else if (line.charAt(indexMatchParent) == '(') {
                        parentCounter++;
                    }
                }
                part = line.substring(0, indexFirstParent).trim();
                if (!part.isEmpty()) {
                    terms.addAll(Arrays.asList(part.trim().split(PATTERN)));
                }

                part = line.substring(indexFirstParent + 1, indexMatchParent);
                terms.add(splitEquation(part));

                line = line.substring(indexMatchParent + 1);
                indexFirstParent = line.indexOf("(");
            } while (indexFirstParent != -1);

            if (!line.trim().isEmpty()) {
                terms.addAll(Arrays.asList(line.trim().split(PATTERN)));
            }
        }
        return terms;
    }

    /**
     * Process a LinkedList containing the terms of an equation.
     *
     * @cleber
     * @param equationTerms The LinkedList containing the terms of an equation.
     * @return The result of the equation processing.
     * @throws InvalidParameterException if some error if found when the
     * equation is processed.
     */
    public Double processTerms(LinkedList equationTerms) throws InvalidParameterException {
        if (equationTerms.size() < 2) {
            throw new InvalidParameterException("invalid number of elements in the equation");
        }

        Double result = 0.0;
        int operation;

        for (operation = 0; operation < VALID_OPERATIONS.length; operation++) {
            if (VALID_OPERATIONS[operation].equalsIgnoreCase(((String) equationTerms.get(0)))) {
                break;
            }
        }
        LinkedList<Double> numbers = new LinkedList<>();
        for (int index = 1; index < equationTerms.size(); index++) {
            if (equationTerms.get(index) instanceof LinkedList) {
                numbers.add(processTerms((LinkedList) equationTerms.get(index)));
            } else if (((String) equationTerms.get(index)).startsWith("!")) {
                Double historyResult = searchHist(Integer.parseInt(
                        ((String) equationTerms.get(index)).substring(1)));
                if (historyResult.equals(Double.NaN)) {
                    throw new InvalidParameterException((String) equationTerms.get(index)
                            + " does not refer to a result in the history");
                }
                numbers.add(historyResult);
            } else {
                numbers.add(Double.parseDouble((String) equationTerms.get(index)));
            }
        }

        Double numbersList[] = new Double[numbers.size()];
        for (int index = 0; index < numbers.size(); index++) {
            numbersList[index] = numbers.get(index);
        }

        switch (operation) {
            case 0:

                result = addNum(numbersList);
                break;
            case 1:
                result = subNum(numbersList);
                break;
            case 2:
                result = multNum(numbersList);
                break;
            case 3:
                result = divideNum(numbersList);
        }

        return result;
    }

    /**
     * This method returns the sum of an array of numbers. The numbers are
     * summed in order of their appearance in the array. This method implements
     * the User Story 1.
     *
     * @author cleber
     * @param nums An array of numbers to sum in the order they appear in the
     * array.
     * @return The sum of all the numbers in the array.
     */
    private double addNum(Double nums[]) {
        double total = 0.0;
        for (double n : nums) {
            total = total + n;
        }
        return total;
    }

    /**
     * This method returns the subtraction of an array of numbers. The numbers
     * are subtracted in order of their appearance in the array. This method
     * implements the User Story 2.
     *
     * @author cleber
     * @param nums An array of numbers to subtract in the order they appear in
     * the array.
     * @return The subtraction of all the numbers in the array.
     */
    private double subNum(Double nums[]) {
        double total = nums[0];
        for (int index = 1; index < nums.length; index++) {
            total = total - nums[index];
        }
        return total;
    }

    //************************************************************************************
    //Use case 3
    /**
     * This method returns the product of an array of numbers. The numbers are
     * multiplied in order of their appearance in the array.
     *
     * @author hornl
     * @param nums An array of numbers to multiply in the order they are to be
     * multiplied
     * @return The product of all the numbers in the array, or NaN if the array
     * is null or has a length less than 1
     */
    private double multNum(Double[] nums) {
        double total = 1;

        for (double n : nums) {
            total = total * n;
        }

        return total;
    }

    //Use case 4
    /**
     * This method returns the quotient of an array of numbers. The numbers are
     * divided in order of their appearance in the array.
     *
     * @author hornl
     * @param nums An array of numbers to divide in the order they are to be
     * multiplied
     * @return The quotient of all the numbers in the array, or NaN if the array
     * is null or has a length less than 1
     */
    private double divideNum(Double[] nums) {
        double total = nums[0];

        if (nums.length <= 1) {
            total = Double.NaN;
        }

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == 0) {
                total = Double.NaN;
                i = nums.length;
            } else {
                total = total / nums[i];
            }
        }

        return total;
    }

    //Use case 5
    /**
     * This Method will loop though the history and display it It displays both
     * the equation and the answer to said equation.
     */
    private void displayHist() {

        //Check to make sure there is something in the history
        if (historyEquations.size() == 0) {
            System.out.println("There is Nothing in History");

            //Check to make sure that there is an equal number of equations and answers
        } else {
            for (int i = 0; i < historyEquations.size(); i++) {
                System.out.println(i + 1 + ". " + historyEquations.get(i));
                System.out.println("\t " + historyAnswers.get(i) + "\n");
            }
        }
    }

    //Use case 6
    /**
     * This method will clear the history by clearing both of the list
     * containing the equations and answers
     *
     * @author hornl
     */
    private void clearHist() {
        historyEquations.clear();
        historyAnswers.clear();
        System.out.println("history cleared");
    }

    //Use case 7
    /**
     * This method will return the answer from a specified location in history
     *
     * @param answerLocation the location to retrieve the answer from (indexed
     * from 1)
     * @return The answer to a previous calculation, or NaN if the list is null
     * or the size is bigger than the history
     */
    private double searchHist(int answerLocation) {
        double answ = 0.0;

        // Make sure that the answerlocation is valid
        if (answerLocation > 0 && historyAnswers.size() >= answerLocation - 1) {
            answ = historyAnswers.get(answerLocation - 1);
            //Otherwise note that that is not a valid entry in the history
        } else if (answerLocation <= 0 || answerLocation - 1 > historyAnswers.size()) {
            System.out.println("The specified location is greater than the size of the history");
            answ = Double.NaN;
        }
        return answ;
    }

}
