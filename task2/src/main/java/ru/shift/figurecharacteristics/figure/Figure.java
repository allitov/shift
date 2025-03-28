package ru.shift.figurecharacteristics.figure;

public abstract class Figure {

    protected FigureType type;
    protected double area;
    protected double perimeter;

    public String getName() {
        return type.getName();
    }

    public double getArea() {
        return area;
    }

    public double getPerimeter() {
        return perimeter;
    }
}
