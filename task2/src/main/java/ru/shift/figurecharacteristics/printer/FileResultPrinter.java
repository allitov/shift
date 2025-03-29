package ru.shift.figurecharacteristics.printer;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class FileResultPrinter implements ResultPrinter {

    private final String filepath;

    public FileResultPrinter(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public void print(String text) {
        try (FileWriter fw = new FileWriter(filepath)) {
            fw.write(text);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
