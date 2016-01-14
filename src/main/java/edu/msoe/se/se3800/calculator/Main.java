package edu.msoe.se.se3800.calculator;

import java.util.Scanner;

/**
 * This class will run the calculator code and send the input to the correct
 * methods
 *
 * @author hornl
 */
public class Main {

    /**
     * This method takes in input and calls the getInput method to redirect to
     * the correct methods.
     *
     * @param args the command line arguments
     * @author hornl
     */
    public static void main(String[] args) {
        Calculator c = new Calculator();

        Scanner in = new Scanner(System.in);
        System.out.println("Please enter an equation, \n or \"cmd\" to list operations, \n or \"quit\" to exit\n");

        boolean keepGoing = true;
        //Loop for input
        while (keepGoing) {
            String s = in.nextLine();
            keepGoing = getInput(s, c);
        }
        //Close scanner
        in.close();
    }

    /**
     * Prints a list of possible commands
     *
     * @return true to show the commands were printed properly
     * @author hornl
     */
    private static boolean printCommands() {
        System.out.println("Command \t Format");
        System.out.println("Addition\t add 1 2 3");
        System.out.println("Subtraction\t sub 1 2 3");
        System.out.println("Multiplication\t mul 1 2 3");
        System.out.println("Division\t div 1 2 3");
        System.out.println("Inner operation\t add 1 (add 1 2) 3");
        System.out.println("Show History\t hist");
        System.out.println("Clear History\t clear");
        System.out.println("history entry n\t add 1 !n");

        return true;
    }

    /**
     * This method will take in a string and decides what to do with the input.
     *
     * @author hornl
     */
    public static boolean getInput(String s, Calculator c) {
        //if quit is entered, exit program 
        if ("quit".equalsIgnoreCase(s)) {
            System.out.println("Goodbye!");
            return false;

            //if cmd is entered, show a list of possible commands 
        } else if ("cmd".equalsIgnoreCase(s)) {
            printCommands();

        } //Otherwise send the input to the parser
        else {
            double d = 0;
            try {
                d = c.solveEquation(s);
                System.out.println(d);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return true;
    }

}
