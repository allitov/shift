package ru.shift;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int size = 0;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter a size of a multiplication table (int from 1 to 32): ");
            size = scanner.nextInt();
            if (size < 1 || size > 32) {
                throw new InputMismatchException();
            }
        } catch (InputMismatchException e) {
            System.err.println("Illegal input format");
            System.exit(0);
        }

        MultiplicationTable mt = new MultiplicationTable();
        mt.printTable(size);
    }
}