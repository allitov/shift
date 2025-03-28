package ru.shift.figurecharacteristics.figure;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Triangle extends Figure {

    private double a;
    private double b;
    private double c;
    private Map<Double, Double> sideToAngle;

    public static Triangle readTriangle(BufferedReader reader) throws IOException {
        String[] params = reader.readLine().split(DELIMITER);
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

    private Triangle(double a, double b, double c) {
        validate(a, b, c);
        this.a = a;
        this.b = b;
        this.c = c;
        this.name = "Треугольник";
        this.perimeter = a + b + c;
        double p = this.perimeter / 2;
        this.area = Math.sqrt(p * (p - a) * (p - b) * (p - c));
        this.sideToAngle = calculateAngles(a, b, c);
    }

    @Override
    public String getFormattedData() {
        return "Тип фигуры: " + name + LINE_SEPARATOR +
                "Площадь: " + DECIMAL_FORMAT.format(area) + " кв. " + UNITS + LINE_SEPARATOR +
                "Периметр: " + DECIMAL_FORMAT.format(perimeter) + " " + UNITS + LINE_SEPARATOR +
                "Сторона a: " + DECIMAL_FORMAT.format(a) + " " + UNITS + "; угол: " + DECIMAL_FORMAT.format(sideToAngle.get(a)) + " градусов" + LINE_SEPARATOR +
                "Сторона b: " + DECIMAL_FORMAT.format(b) + " " + UNITS + "; угол: " + DECIMAL_FORMAT.format(sideToAngle.get(b)) + " градусов" + LINE_SEPARATOR +
                "Сторона c: " + DECIMAL_FORMAT.format(c) + " " + UNITS + "; угол: " + DECIMAL_FORMAT.format(sideToAngle.get(c)) + " градусов" + LINE_SEPARATOR;
    }

    private void validate(double a, double b, double c) {
        if (a <= 0) {
            throw new IllegalArgumentException("Triangle side must be greater than 0. Got: " + a);
        }
        if (b <= 0) {
            throw new IllegalArgumentException("Triangle side must be greater than 0. Got: " + b);
        }
        if (c <= 0) {
            throw new IllegalArgumentException("Triangle side must be greater than 0. Got: " + c);
        }
        if (a >= b + c || b >= a + c || c >= a + b) {
            throw new IllegalArgumentException("Triangle inequality error");
        }
    }

    private Map<Double, Double> calculateAngles(double a, double b, double c) {
        Map<Double, Double> angles = new HashMap<>();
        angles.put(a, calculateAngle(a, b, c));
        angles.put(b, calculateAngle(b, c, a));
        angles.put(c, calculateAngle(c, a, b));

        return angles;
    }

    private double calculateAngle(double oppositeSide, double adjacentLeft, double adjacentRight) {
        double numerator = adjacentLeft * adjacentLeft + adjacentRight * adjacentRight - oppositeSide * oppositeSide;
        double denominator = 2 * adjacentLeft * adjacentRight;

        return Math.acos(numerator / denominator);
    }

    public Map<Double, Double> getSideToAngle() {
        return Collections.unmodifiableMap(sideToAngle);
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }
}
