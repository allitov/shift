package ru.shift.figurecharacteristics.factory;

import ru.shift.figurecharacteristics.figure.Circle;

import java.io.BufferedReader;
import java.io.IOException;

public class CircleFactory {

    private static final String DELIMITER = " ";

    public static Circle create(BufferedReader reader) throws IOException {
        String[] params = reader.readLine().split(DELIMITER);
        if (params.length != 1) {
            throw new IllegalArgumentException("Circle expects 1 parameter. Got " + params.length);
        }

        double radius;
        try {
            radius = Double.parseDouble(params[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Circle radius expects a number. Got radius = " + params[0]);
        }

        return new Circle(radius);
    }
}
