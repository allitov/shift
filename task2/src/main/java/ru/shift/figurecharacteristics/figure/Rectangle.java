package ru.shift.figurecharacteristics.figure;

public class Rectangle extends Figure {

    private final double a;
    private final double b;
    private final double diagonal;
    private final double length;
    private final double width;

    public Rectangle(double a, double b) {
        validate(a, b);
        this.a = a;
        this.b = b;
        this.type = FigureType.RECTANGLE;
        this.area = calculateArea();
        this.perimeter = calculatePerimeter();
        this.diagonal = Math.sqrt((a * a) + (b * b));
        this.length = Math.max(a, b);
        this.width = Math.min(a, b);
    }

    @Override
    protected double calculateArea() {
        return a * b;
    }

    @Override
    protected double calculatePerimeter() {
        return (a + b) * 2;
    }

    private void validate(double a, double b) {
        if (a <= 0) {
            throw new IllegalArgumentException("Rectangle side must be greater than 0. Got " + a);
        }
        if (b <= 0) {
            throw new IllegalArgumentException("Rectangle side must be greater than 0. Got " + b);
        }
    }

    public double getDiagonal() {
        return diagonal;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }
}
