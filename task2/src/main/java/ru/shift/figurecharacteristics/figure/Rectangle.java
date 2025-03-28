package ru.shift.figurecharacteristics.figure;

public class Rectangle extends Figure {

    private double diagonal;
    private double length;
    private double width;

    public Rectangle(double a, double b) {
        validate(a, b);
        this.type = FigureType.RECTANGLE;
        this.area = a * b;
        this.perimeter = (a + b) * 2;
        this.diagonal = Math.sqrt((a * a) + (b * b));
        this.length = Math.max(a, b);
        this.width = Math.min(a, b);
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
