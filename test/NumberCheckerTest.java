package test;

import java.io.*;

public class NumberCheckerTest {

    static int passed = 0;
    static int failed = 0;

    public static void main(String[] args) throws Exception {
        // checkNumber tests
        testPositive();
        testNegative();
        testZero();

        // main method tests
        testMainPositive();
        testMainNegative();
        testMainZero();
        testMainInvalidInput();
        testMainStop();

        System.out.println("\n--- Results ---");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);

        if (failed > 0) {
            System.exit(1);
        }
    }

    static void check(String testName, String expected, String actual) {
        if (expected.equals(actual)) {
            System.out.println("PASS: " + testName);
            passed++;
        } else {
            System.out.println("FAIL: " + testName + " | Expected: " + expected + " | Got: " + actual);
            failed++;
        }
    }

    static void testPositive() {
        check("Positive number", "positive", NumberChecker.checkNumber(5));
    }

    static void testNegative() {
        check("Negative number", "negative", NumberChecker.checkNumber(-3));
    }

    static void testZero() {
        check("Zero", "zero", NumberChecker.checkNumber(0));
    }

    static void checkMain(String testName, String input, String expectedOutput) throws Exception {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        ByteArrayInputStream fakeInput = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream fakeOutput = new ByteArrayOutputStream();

        System.setIn(fakeInput);
        System.setOut(new PrintStream(fakeOutput));

        NumberChecker.main(new String[]{});

        System.setIn(originalIn);
        System.setOut(originalOut);

        String output = fakeOutput.toString().trim();
        if (output.contains(expectedOutput)) {
            System.out.println("PASS: " + testName);
            passed++;
        } else {
            System.out.println("FAIL: " + testName + " | Expected output to contain: " + expectedOutput + " | Got: " + output);
            failed++;
        }
    }

    static void testMainPositive() throws Exception {
        checkMain("Main positive number", "5\nstop\n", "positive");
    }

    static void testMainNegative() throws Exception {
        checkMain("Main negative number", "-3\nstop\n", "negative");
    }

    static void testMainZero() throws Exception {
        checkMain("Main zero", "0\nstop\n", "zero");
    }

    static void testMainInvalidInput() throws Exception {
        checkMain("Main invalid input", "abc\n5\nstop\n", "positive");
    }

    static void testMainStop() throws Exception {
        checkMain("Main stop", "stop\n", "");
    }
}
