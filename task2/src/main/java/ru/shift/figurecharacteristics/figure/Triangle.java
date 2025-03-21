package ru.shift.figurecharacteristics.figure;

import ru.shift.figurecharacteristics.input.FigureData;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Triangle extends Figure {

    private final Map<Double, Double> sideToAngle;

    public Triangle(FigureData data) {
        super(data);
        sideToAngle = calculateSideToAngle(data.getParams());
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected boolean areParamsValid(double[] params) {
        if (params.length != 3) {
            return false;
        }
        double a = params[0];
        double b = params[1];
        double c = params[2];

        return a + b > c && a + c > b && b + c > a;
    }

    @Override
    protected double calculateArea(double[] params) {
        double a = params[0];
        double b = params[1];
        double c = params[2];
        double halfPerimeter = calculatePerimeter(params) / 2;

        return Math.sqrt(halfPerimeter * (halfPerimeter - a) * (halfPerimeter - b) * (halfPerimeter - c));
    }

    @Override
    protected double calculatePerimeter(double[] params) {
        double a = params[0];
        double b = params[1];
        double c = params[2];

        return a + b + c;
    }

    private Map<Double, Double> calculateSideToAngle(double[] params) {
        Map<Double, Double> sideToAngle = new HashMap<>();
        double a = params[0];
        double b = params[1];
        double c = params[2];
        sideToAngle.put(a, calculateAngle(b, c, a));
        sideToAngle.put(b, calculateAngle(a, c, b));
        sideToAngle.put(c, calculateAngle(a, b, c));

        return sideToAngle;
    }

    private double calculateAngle(double left, double right, double bottom) {
        return (left * left + right * right - bottom * bottom) / (2 * left * right);
    }

    public Map<Double, Double> getSideToAngle() {
        return Collections.unmodifiableMap(sideToAngle);
    }
}
