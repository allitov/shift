package ru.shift.figurecharacteristics.figure;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Triangle extends Figure {

    private final double a;
    private final double b;
    private final double c;
    private final Map<Double, Double> sideToAngleMap;

    public Triangle(double a, double b, double c) {
        validate(a, b, c);
        this.a = a;
        this.b = b;
        this.c = c;
        this.type = FigureType.TRIANGLE;
        this.perimeter = a + b + c;
        double p = this.perimeter / 2;
        this.area = Math.sqrt(p * (p - a) * (p - b) * (p - c));
        this.sideToAngleMap = calculateAngles(a, b, c);
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

    public Map<Double, Double> getSideToAngleMap() {
        return Collections.unmodifiableMap(sideToAngleMap);
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
