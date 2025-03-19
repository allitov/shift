package ru.shift;

import ru.shift.multiplicationtable.MultiplicationTable;
import ru.shift.multiplicationtable.SizeReader;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    private static final InputStream DEFAULT_INPUT_STREAM = System.in;
    private static final OutputStream DEFAULT_OUTPUT_STREAM = System.out;

    public static void main(String[] args) {
        try (PrintWriter writer = new PrintWriter(DEFAULT_OUTPUT_STREAM);
             Scanner scanner = new Scanner(DEFAULT_INPUT_STREAM)) {
            SizeReader sizeReader = new SizeReader(scanner, writer);
            int size = sizeReader.readSize();
            MultiplicationTable multiplicationTable = new MultiplicationTable(writer);
            multiplicationTable.print(size);
        }
    }
}