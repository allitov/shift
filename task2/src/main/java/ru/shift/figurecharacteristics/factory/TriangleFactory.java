package ru.shift.figurecharacteristics.factory;

import ru.shift.figurecharacteristics.figure.FigureType;
import ru.shift.figurecharacteristics.figure.Triangle;

import java.io.BufferedReader;
import java.io.IOException;

public class TriangleFactory implements FigureFactory<Triangle> {

    @Override
    public FigureType getType() {
        return FigureType.TRIANGLE;
    }

    @Override
    public Triangle read(BufferedReader reader) throws IOException {
        String[] params = reader.readLine().split(PARAMS_DELIMITER);
        if (params.length != 3) {
            throw new IllegalArgumentException("Triangle expects 3 parameters. Got " + params.length);
        }

        double a, b, c;
        try {
            a = Double.parseDouble(params[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Triangle side expects a number. Got a = " + params[0]);
        }
        try {
            b = Double.parseDouble(params[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Triangle side expects a number. Got b = " + params[1]);
        }
        try {
            c = Double.parseDouble(params[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Triangle side expects a number. Got c = " + params[2]);
        }

        return new Triangle(a, b, c);
    }
}
