package ru.shift.figurecharacteristics.figure;

public class Rectangle extends Figure {

    private final double diagonal;
    private final double length;
    private final double width;

    public Rectangle(double[] params) {
        super(FigureType.RECTANGLE, params);
        this.diagonal = calculateDiagonal(params);
        this.length = Math.max(params[0], params[1]);
        this.width = Math.min(params[0], params[1]);
    }

    @Override
    public String toString() {
        return super.toString() +
                String.format("%nДиагональ: %.2f мм%nДлина: %.2f мм%nШирина: %.2f мм", diagonal, length, width);
    }

    @Override
    protected boolean areParamsValid(double[] params) {
        return params.length == 2;
    }

    @Override
    protected double calculateArea(double[] params) {
        double a = params[0];
        double b = params[1];

        return a * b;
    }

    @Override
    protected double calculatePerimeter(double[] params) {
        double a = params[0];
        double b = params[1];

        return (a + b) * 2;
    }

    private double calculateDiagonal(double[] params) {
        double a = params[0];
        double b = params[1];

        return Math.sqrt(a * a + b * b);
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
