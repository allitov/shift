package ru.shift.figurecharacteristics.figure;

import java.util.Objects;

public abstract class Figure {

    private final FigureType type;
    private final double area;
    private final double perimeter;

    public Figure(FigureType type, double[] params) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(params);
        if (!areParamsValid(params)) {
            System.err.println("Invalid parameters for " + getClass().getSimpleName());
            throw new IllegalArgumentException();
        }
        this.type = type;
        this.area = calculateArea(params);
        this.perimeter = calculatePerimeter(params);
    }

    @Override
    public String toString() {
        return String.format("Тип фигуры: %s%nПлощадь: %.2f кв. мм%nПериметр: %.2f мм", type, area, perimeter);
    }

    protected abstract boolean areParamsValid(double[] params);

    protected abstract double calculateArea(double[] params);

    protected abstract double calculatePerimeter(double[] params);

    public FigureType getType() {
        return type;
    }

    public double getArea() {
        return area;
    }

    public double getPerimeter() {
        return perimeter;
    }
}
