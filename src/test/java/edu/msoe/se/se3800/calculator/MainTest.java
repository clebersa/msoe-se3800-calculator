package edu.msoe.se.se3800.calculator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Class responsible to test the Main class.
 *
 * @author cleber
 */
public class MainTest {

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
