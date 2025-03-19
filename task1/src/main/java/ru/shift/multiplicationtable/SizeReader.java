package ru.shift.multiplicationtable;

import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SizeReader {

    private static final int SIZE_MIN_VALUE = 1;
    private static final int SIZE_MAX_VALUE = 32;

    private final Scanner scanner;
    private final PrintWriter writer;

    public SizeReader(Scanner scanner, PrintWriter writer) {
        this.scanner = scanner;
        this.writer = writer;
    }

    public int readSize() {
        String input = "";
        try {
            writer.print("Enter a size of a multiplication table (int from "
                    + SIZE_MIN_VALUE + " to " + SIZE_MAX_VALUE + "): ");
            writer.flush();
            input = scanner.next();
            int size = Integer.parseInt(input);
            if (size < SIZE_MIN_VALUE || size > SIZE_MAX_VALUE) {
                throw new InputMismatchException();
            }
            return size;
        } catch (NumberFormatException | InputMismatchException e) {
            throw new InputMismatchException("Invalid input: '" + input + "'. Must be an integer between "
                    + SIZE_MIN_VALUE + " and " + SIZE_MAX_VALUE);
        }
    }
}
