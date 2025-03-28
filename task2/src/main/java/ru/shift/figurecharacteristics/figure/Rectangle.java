package ru.shift.figurecharacteristics.figure;

import java.io.BufferedReader;
import java.io.IOException;

public class Rectangle extends Figure {

    private double diagonal;
    private double length;
    private double width;

    public static Rectangle readRectangle(BufferedReader reader) throws IOException {
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

    private Rectangle(double a, double b) {
        validate(a, b);
        this.name = "Прямоугольник";
        this.area = a * b;
        this.perimeter = (a + b) * 2;
        this.diagonal = Math.sqrt((a * a) + (b * b));
        this.length = Math.max(a, b);
        this.width = Math.min(a, b);
    }

    private void validate(double a, double b) {
        if (a <= 0) {
            throw new IllegalArgumentException("Rectangle side must be greater than 0. Got " + a);
        }
        if (b <= 0) {
            throw new IllegalArgumentException("Rectangle side must be greater than 0. Got " + b);
        }
    }

    public double getDiagonal() {
        return diagonal;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }
}
