package ru.shift.figurecharacteristics.figure;

import java.io.BufferedReader;
import java.io.IOException;

public class Circle extends Figure {

    private double radius;
    private double diameter;

    public static Circle readCircle(BufferedReader reader) throws IOException {
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

    private Circle(double radius) {
        validate(radius);
        this.name = "Круг";
        this.area = Math.PI * radius * radius;
        this.perimeter = 2 * Math.PI * radius;
        this.radius = radius;
        this.diameter = radius * 2;
    }

    private void validate(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be greater than 0. Got " + radius);
        }
    }

    public double getRadius() {
        return radius;
    }

    public double getDiameter() {
        return diameter;
    }
}
