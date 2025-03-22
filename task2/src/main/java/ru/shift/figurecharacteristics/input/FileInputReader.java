package ru.shift.figurecharacteristics.input;

import ru.shift.figurecharacteristics.figure.entity.FigureType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileInputReader implements InputReader {

    private static final String PARAMS_DELIMITER = " ";

    private final File file;

    public FileInputReader(String filename) {
        file = new File(filename);
    }

    @Override
    public FigureData getInputData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            FigureType type = FigureType.valueOf(reader.readLine());
            String[] params = reader.readLine().split(PARAMS_DELIMITER);
            return new FigureData(type, params);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + file.getAbsolutePath());
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.err.println("Error while reading file: " + file.getAbsolutePath());
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            System.err.println("Illegal argument: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
