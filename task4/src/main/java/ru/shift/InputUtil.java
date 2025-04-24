package ru.shift;

import java.util.InputMismatchException;
import java.util.Scanner;

public final class InputUtil {

    private InputUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static long readLong() {
        Scanner sc = new Scanner(System.in);
        long number = 0;
        while (number <= 0) {
            System.out.print("Please, enter a natural number: ");
            try {
                number = sc.nextLong();
            } catch (InputMismatchException ignored) {
            }
        }

        return number;
    }
}
