package ru.shift.figurecharacteristics.figure;

public class Circle extends Figure {

    private double radius;
    private double diameter;

    public Circle(double radius) {
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
