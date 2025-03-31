package ru.shift.figurecharacteristics.figure;

public abstract class Figure {

    protected FigureType type;
    protected double area;
    protected double perimeter;

    protected abstract double calculateArea();

    protected abstract double calculatePerimeter();

    public String getName() {
        return type.getName();
    }

    public FigureType getType() {
        return type;
    }

    public double getArea() {
        if (area == 0) {
            return calculateArea();
        }
        return area;
    }

    public double getPerimeter() {
        if (perimeter == 0) {
            return calculatePerimeter();
        }
        return perimeter;
    }
}
