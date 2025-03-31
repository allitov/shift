package ru.shift.figurecharacteristics.figure;

public class Circle extends Figure {

    private final double radius;
    private final double diameter;

    public Circle(double radius) {
        validate(radius);
        this.radius = radius;
        this.type = FigureType.CIRCLE;
        this.area = calculateArea();
        this.perimeter = calculatePerimeter();
        this.diameter = radius * 2;
    }

    @Override
    protected double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    protected double calculatePerimeter() {
        return 2 * Math.PI * radius;
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
