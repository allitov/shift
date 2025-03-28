package ru.shift.figurecharacteristics.figure;

public abstract class Figure {

    protected String name;
    protected double area;
    protected double perimeter;

    public String getName() {
        return name;
    }

    public double getArea() {
        return area;
    }

    public double getPerimeter() {
        return perimeter;
    }
}
