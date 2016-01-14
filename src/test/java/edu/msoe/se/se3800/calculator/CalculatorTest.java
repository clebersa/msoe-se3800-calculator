package edu.msoe.se.se3800.calculator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class responsible to test the Calculator class.
 *
 * @author cleber
 */
public class CalculatorTest {

    /**
     * Output stream used to catch the console messages.
     */
    private ByteArrayOutputStream sysout;

    /**
     * Input stream used to insert information in the console.
     */
    private ByteArrayInputStream sysin;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        sysout = new ByteArrayOutputStream();
        System.setOut(new PrintStream(sysout, true, "UTF8"));
    }

    @After
    public void tearDown() {
        System.setOut(System.out);
    }

    @Test(expected = InvalidParameterException.class)
    public void testParseEquationParen6() {
        Calculator instance = new Calculator();
        instance.solveEquation("add 3 (add 4 5 )");
    }

    @Test(expected = InvalidParameterException.class)
    public void testParseEquationParen7() {
        Calculator instance = new Calculator();
        instance.solveEquation("add 3 (add 4 ( 5)");
    }

    @Test
    public void testParseEquationParen8() {
        Calculator instance = new Calculator();
        assertEquals(30, instance.solveEquation("add 1 2 (add 10 20) 3 4 (sub 30 40)"), 0);
    }

    @Test
    public void testParseEquationParen10() {
        Calculator instance = new Calculator();
        assertEquals(20033, instance.solveEquation("add 1 2 (add 10 20 (mul 100 200))"), 0);
    }

    @Test(expected = InvalidParameterException.class)
    public void testParseEquationParen11() {
        Calculator instance = new Calculator();
        instance.solveEquation("add 1 2 (add 10 20 (mul 100 200)?)");
    }

    @Test(expected = InvalidParameterException.class)
    public void testParseEquationParen12() {
        Calculator instance = new Calculator();
        //cover break
        instance.solveEquation("add 3 (add 4 (add 5)");
    }

    /**
     * Test solveEquation with null equation.
     *
     * @author cleber
     */
    @Test(expected = InvalidParameterException.class)
    public void testParseEquationNullEquation() {
        Calculator instance = new Calculator();
        instance.solveEquation(null);
    }

    /**
     * Test solveEquation with empty equation.
     *
     * @author cleber
     */
    @Test(expected = InvalidParameterException.class)
    public void testParseEquationEmptyEquation1() {
        Calculator instance = new Calculator();
        instance.solveEquation("");
    }

    /**
     * Test solveEquation with a equation filled with tab.
     *
     * @author cleber
     */
    @Test(expected = InvalidParameterException.class)
    public void testParseEquationEmptyEquation2() {
        Calculator instance = new Calculator();
        instance.solveEquation("\t");
    }

    /**
     * Test solveEquation with a equation filled with tabs and spaces.
     *
     * @author cleber
     */
    @Test(expected = InvalidParameterException.class)
    public void testParseEquationEmptyEquation3() {
        Calculator instance = new Calculator();
        instance.solveEquation("  \t \t\t  \t");
    }

    /**
     * Test the solveEquation method with incorrect strings
     *
     * @author hornl
     */
    @Test(expected = InvalidParameterException.class)
    public void testParseEquationNuNum() {
        Calculator instance = new Calculator();
        instance.solveEquation("add");
    }

    @Test(expected = InvalidParameterException.class)
    public void testParseEquationLetters1() {
        Calculator instance = new Calculator();
        instance.solveEquation("t");
    }

    @Test(expected = InvalidParameterException.class)
    public void testParseEquationLetters2() {
        Calculator instance = new Calculator();
        instance.solveEquation("tell");
    }

    @Test(expected = InvalidParameterException.class)
    public void testParseEquationLetters3() {
        Calculator instance = new Calculator();
        instance.solveEquation("Hello Person");
    }

    @Test(expected = InvalidParameterException.class)
    public void testParseEquationLetters4() {
        Calculator instance = new Calculator();
        instance.solveEquation("(HI) 2 3 5");
    }

    @Test(expected = InvalidParameterException.class)
    public void testParseEquationLetters5() {
        Calculator instance = new Calculator();
        instance.solveEquation("all 3 5 2");
    }

    @Test(expected = InvalidParameterException.class)
    public void testParseEquationLetters6() {
        Calculator instance = new Calculator();
        instance.solveEquation("add a t t");
    }

    @Test(expected = InvalidParameterException.class)
    public void testParseEquationLetters7() {
        Calculator instance = new Calculator();
        instance.solveEquation("add 1 t 3");
    }

    /**
     * Test Parse Equation with parenthesis
     *
     * @author hornl
     */
    @Test
    public void testParseEquationParen1() {
        Calculator instance = new Calculator();

        assertEquals(8, instance.solveEquation("add (add 1 2) 5"), 0);
    }

    @Test(expected = InvalidParameterException.class)
    public void testParseEquationParen2() {
        Calculator instance = new Calculator();
        instance.solveEquation("add ( )");
    }

    @Test(expected = InvalidParameterException.class)
    public void testParseEquationParen3() {
        Calculator instance = new Calculator();
        instance.solveEquation("add 3 (add 4 5))");
    }

    @Test(expected = InvalidParameterException.class)
    public void testParseEquationParen4() {
        Calculator instance = new Calculator();
        instance.solveEquation("add 3 ((add 4 5)");
    }

    @Test
    public void testParseEquationParen5() {
        Calculator instance = new Calculator();
        //Acceptance criteria 8.a
        assertEquals(9, instance.solveEquation("add 1 3 (add 2 3)"), 0);
    }

    /**
     * Tests to test the AddNum method
     *
     * @author hornl
     */
    @Test
    public void testAddNum() {
        Calculator instance = new Calculator();
        assertEquals(3, instance.solveEquation("add 1 2 "), 0);
        //Acceptance criteria 1.a
        assertEquals(9, instance.solveEquation("add 1 3 5"), 0);
        assertEquals(15, instance.solveEquation("add 1 2 5 7 "), 0);
        assertEquals(11, instance.solveEquation("add 1 10"), 0);
        assertEquals(12, instance.solveEquation("add 10 2 "), 0);
        assertEquals(1, instance.solveEquation("add -1 2 "), 0);
        assertEquals(8, instance.solveEquation("add 10 -2 "), 0);
        assertEquals(10, instance.solveEquation("add 10 0 "), 0);
    }

    /**
     * Tests to test the SubNum method
     *
     * @author hornl
     */
    @Test
    public void testSubNum() {
        Calculator instance = new Calculator();
        assertEquals(1, instance.solveEquation("sub 2 1 "), 0);
        assertEquals(-7, instance.solveEquation("sub 1 3 5 "), 0);
        assertEquals(2, instance.solveEquation("sub 7 2 3 0 "), 0);
        assertEquals(12, instance.solveEquation("sub 10 -2"), 0);
        assertEquals(-12, instance.solveEquation("sub -10 2 "), 0);
        assertEquals(-8, instance.solveEquation("sub -10 -2 "), 0);
        assertEquals(0, instance.solveEquation("sub -7 -7 "), 0);
    }

    /**
     * Tests the History Parser
     *
     * @author hornl
     */
    @Test
    public void testHistoryParser() {
        Calculator instance = new Calculator();
        instance.solveEquation("add 1 3");
        instance.solveEquation("add 20 9");
        instance.solveEquation("add 20 9");
        instance.solveEquation("add 1 1");
        //Acceptance criteria 7.a,b,c,d
        assertEquals(32, instance.solveEquation("add 1 2 !3"), 0);
        assertEquals(8, instance.solveEquation("mul 1 2 !1"), 0);
        assertEquals(-30, instance.solveEquation("sub 1 2 !2"), 0);
        assertEquals(2, instance.solveEquation("div 20 5 !4"), 0);
    }

    /**
     * Tests to the multNum method - BEGINNING
     *
     * @author cleber
     */
    @Test
    public void testMultNum() {
        Calculator instance = new Calculator();

        // Acceptance criteria 3.a
        assertEquals(15, instance.solveEquation("mul 1 3 5"), 0);

        assertEquals(0, instance.solveEquation("mul 0"), 0);
        assertEquals(0, instance.solveEquation("mul 0 2 3"), 0);
        assertEquals(0.002, instance.solveEquation("mul 2 0.1 0.01"), 0);
        assertEquals(-0.002, instance.solveEquation("mul -2 -0.1 -0.01"), 0);
    }
    // Tests to the multNum method - END

    /**
     * Tests to the divideNum method - BEGINNING
     *
     * @author cleber
     */
    @Test
    public void testDivideNum() {
        Calculator instance = new Calculator();

        // Acceptance criteria 4.a
        assertEquals(1, instance.solveEquation("div 8 4 2"), 0);
        // Acceptance criteria 4.b
        assertEquals(Double.NaN, instance.solveEquation("div 9 0 2"), 0);

        assertEquals(Double.NaN, instance.solveEquation("div 0"), 0);
        assertEquals(5, instance.solveEquation("div 50 5 2"), 0);
        assertEquals(-10, instance.solveEquation("div 100 -5 2"), 0);
        assertEquals(20, instance.solveEquation("div 2 0.1"), 0);
    }
    // Tests to the divideNum method - END

    /**
     * Tests to the displayHist method (hist command) - BEGINNING
     *
     * @author cleber
     */
    @Test
    public void testDisplayHistEmpty() {
        Calculator instance = new Calculator();
        instance.solveEquation("hist");
        assertEquals("There is Nothing in History" + System.lineSeparator(),
                sysout.toString());
    }

    @Test
    public void testDisplayHistEmpty2() {
        Calculator instance = new Calculator();
        instance.solveEquation("  \t hist");
        assertEquals("There is Nothing in History" + System.lineSeparator(),
                sysout.toString());
    }

    @Test
    public void testDisplayHist() {
        Calculator instance = new Calculator();
        StringBuilder sb = new StringBuilder();

        instance.solveEquation("add 1 3 5");
        instance.solveEquation("hist");
        sb.append("1. add 1 3 5");
        sb.append(System.lineSeparator());
        sb.append("\t 9.0\n");
        sb.append(System.lineSeparator());
        assertEquals(sb.toString(), sysout.toString());

        sysout.reset();
        instance.solveEquation("sub 1 3 5");
        instance.solveEquation("hist");
        sb.append("2. sub 1 3 5");
        sb.append(System.lineSeparator());
        sb.append("\t -7.0\n");
        sb.append(System.lineSeparator());
        assertEquals(sb.toString(), sysout.toString());

        sysout.reset();
        instance.solveEquation("mul 1 3 5");
        instance.solveEquation("hist");
        sb.append("3. mul 1 3 5");
        sb.append(System.lineSeparator());
        sb.append("\t 15.0\n");
        sb.append(System.lineSeparator());
        assertEquals(sb.toString(), sysout.toString());
    }
    // Tests to the displayHist method (hist command) - END

    /**
     * Tests to the clearHist method (clear command) - BEGINNING
     *
     * @author cleber
     */
    // User Story 6. Acceptance criteria 6.a
    @Test
    public void testClearHist() {
        Calculator instance = new Calculator();

        instance.solveEquation("add 1 3 5");
        instance.solveEquation("sub 1 3 5");
        instance.solveEquation("mul 1 3 5");

        instance.solveEquation("clear");
        assertEquals("history cleared" + System.lineSeparator(), sysout.toString());

        sysout.reset();
        instance.solveEquation("hist");
        assertEquals("There is Nothing in History" + System.lineSeparator(), sysout.toString());
    }
    // Tests to the clearHist method (clear command) - END

    /**
     * Tests to the searchHist method and ! operand - BEGINNING
     *
     * @author cleber
     */
    //Acceptance Criteria 7.a
    @Test
    public void testSearchOperandAC7a() {
        Calculator instance = new Calculator();

        instance.solveEquation("add 1 2 3");
        instance.solveEquation("sub 6 2 0");
        instance.solveEquation("add 22 5 2");
        instance.solveEquation("add 3 2 0");

        assertEquals(32.0, instance.solveEquation("add 1 2 !3"), 0);
    }

    //Acceptance Criteria 7.b
    @Test
    public void testSearchOperandAC7b() {
        Calculator instance = new Calculator();

        instance.solveEquation("add 5 2 -3");
        instance.solveEquation("sub 6 2 0");

        assertEquals(8.0, instance.solveEquation("mul 1 2 !1"), 0);
    }

    //Acceptance Criteria 7.c
    @Test
    public void testSearchOperandAC7c() {
        Calculator instance = new Calculator();

        instance.solveEquation("add 1 2 3");
        instance.solveEquation("add 22 5 2");
        instance.solveEquation("sub 6 2 0");

        assertEquals(-30.0, instance.solveEquation("sub 1 2 !2"), 0);
    }

    //Acceptance Criteria 7.d
    @Test
    public void testSearchOperandAC7d() {
        Calculator instance = new Calculator();

        instance.solveEquation("add 1 2 3");
        instance.solveEquation("sub 6 2 0");
        instance.solveEquation("add 22 5 2");
        instance.solveEquation("add -1 2 1");

        assertEquals(2.0, instance.solveEquation("div 20 5 !4"), 0);
    }

    @Test(expected = InvalidParameterException.class)
    public void testSearchOperandNoLocationPositive() {
        Calculator instance = new Calculator();

        // Acceptance Criteria 7.e
        instance.solveEquation("div 20 5 !5");
    }

    @Test(expected = InvalidParameterException.class)
    public void testSearchOperandNoLocationNegative() {
        Calculator instance = new Calculator();

        instance.solveEquation("div 20 5 !-5");
    }

    @Test(expected = InvalidParameterException.class)
    public void testSearchOperandNoLocationNull() {
        Calculator instance = new Calculator();

        instance.solveEquation("div 20 ! 5");
    }
    // Tests to the searchHist method and ! operand - BEGINNING

//**************************Test Main Method*********************
    /**
     * Test of main method, of class Main.
     *
     * @author cleber
     * @throws java.io.UnsupportedEncodingException
     */
    @Test
    public void testMain() throws UnsupportedEncodingException {
        sysin = new ByteArrayInputStream("quit".getBytes("UTF8"));
        System.setIn(sysin);
        Main main = new Main();
        Main.main(null);
        String message = "Please enter an equation, \n or \"cmd\" to list "
                + "operations, \n or \"quit\" to exit\n"
                + System.lineSeparator() + "Goodbye!" + System.lineSeparator();

        assertEquals(message, sysout.toString());
    }

    /**
     * Test of main method, of class Main.
     *
     * @author cleber
     * @throws java.io.UnsupportedEncodingException
     */
    @Test
    public void testPrintCommands() throws UnsupportedEncodingException {
        sysin = new ByteArrayInputStream(("cmd" + System.lineSeparator() + "quit").getBytes("UTF8"));
        System.setIn(sysin);
        Main.main(null);
        String message = "Please enter an equation, \n or \"cmd\" to list "
                + "operations, \n or \"quit\" to exit\n"
                + System.lineSeparator() + "Command \t Format" + System.lineSeparator()
                + "Addition\t add 1 2 3" + System.lineSeparator()
                + "Subtraction\t sub 1 2 3" + System.lineSeparator()
                + "Multiplication\t mul 1 2 3" + System.lineSeparator()
                + "Division\t div 1 2 3" + System.lineSeparator()
                + "Inner operation\t add 1 (add 1 2) 3" + System.lineSeparator()
                + "Show History\t hist" + System.lineSeparator()
                + "Clear History\t clear" + System.lineSeparator()
                + "history entry n\t add 1 !n" + System.lineSeparator()
                + "Goodbye!" + System.lineSeparator();
        assertEquals(message, sysout.toString());
    }

    /**
     * Test of getInput method, of class Main.
     *
     * @author cleber
     */
    @Test
    public void testGetInputQuit() {
        String s = "quit";
        Calculator c = new Calculator();
        boolean expResult = false;
        boolean result = Main.getInput(s, c);
        assertEquals(expResult, result);

        String message = "Goodbye!" + System.lineSeparator();
        assertEquals(message, sysout.toString());
    }

    /**
     * Test of getInput method, of class Main, with a null value for the
     * <code>s</code> parameter.
     *
     * @author cleber
     */
    @Test
    public void testGetInputNullS() {
        String s = null;
        Calculator c = new Calculator();
        boolean expResult = true;
        boolean result = Main.getInput(s, c);
        assertEquals(expResult, result);
    }

    /**
     * Test of getInput method, of class Main, with a null value for the
     * <code>c</code> parameter.
     *
     * @author cleber
     */
    @Test
    public void testGetInputNullC() {
        String s = "add 2 3";
        Calculator c = null;
        boolean expResult = true;
        boolean result = Main.getInput(s, c);
        assertEquals(expResult, result);
    }

    /**
     * Test of getInput method, of class Main, with a null value for the
     * <code>s</code> and <code>c</code> parameters.
     *
     * @author cleber
     */
    @Test
    public void testGetInputNullSC() {
        String s = null;
        Calculator c = null;
        boolean expResult = true;
        boolean result = Main.getInput(s, c);
        assertEquals(expResult, result);
    }

    /**
     * Test of getInput method, of class Main, with valid equation.
     *
     * @author cleber
     */
    @Test
    public void testGetInput1() {
        String s = "add 2 3 4";
        Calculator c = new Calculator();
        boolean expResult = true;
        boolean result = Main.getInput(s, c);
        assertEquals(expResult, result);
    }
}
