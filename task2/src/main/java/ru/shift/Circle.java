package ru.shift;

public class Circle extends Figure {

    private final double radius;
    private final double diameter;

    public Circle(FigureData data) {
        super(data);
        this.radius = data.getParams()[0];
        this.diameter = radius * 2;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("%nРадиус: %.2f мм%nДиаметр: %.2f мм", radius, diameter);
    }

    @Override
    protected boolean areParamsValid(double[] params) {
        return params.length == 1;
    }

    @Override
    protected double calculateArea(double[] params) {
        double radius = params[0];
        return Math.PI * radius * radius;
    }

    @Override
    protected double calculatePerimeter(double[] params) {
        double radius = params[0];
        return 2 * Math.PI * radius;
    }

    public double getRadius() {
        return radius;
    }

    public double getDiameter() {
        return diameter;
    }
}
