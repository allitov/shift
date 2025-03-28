package ru.shift.figurecharacteristics.factory;

import ru.shift.figurecharacteristics.figure.FigureType;
import ru.shift.figurecharacteristics.figure.Rectangle;

import java.io.BufferedReader;
import java.io.IOException;

public class RectangleFactory implements FigureFactory<Rectangle> {

    private static final String DELIMITER = " ";

    @Override
    public FigureType getType() {
        return FigureType.RECTANGLE;
    }

    @Override
    public Rectangle read(BufferedReader reader) throws IOException {
        String[] params = reader.readLine().split(DELIMITER);
        if (params.length != 2) {
            throw new IllegalArgumentException("Rectangle expects 2 parameters. Got " + params.length);
        }

        double a, b;
        try {
            a = Double.parseDouble(params[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Rectangle side expects a number. Got a = " + params[0]);
        }
        try {
            b = Double.parseDouble(params[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Rectangle side expects a number. Got b = " + params[1]);
        }

        return new Rectangle(a, b);
    }
}
