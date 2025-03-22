package ru.shift.figurecharacteristics.figure.entity;

public class Triangle extends Figure {

    private final FigureType type;
    private final double area;
    private final double perimeter;

    private Triangle(double a, double b, double c) {
        this.type = FigureType.TRIANGLE;
        this.perimeter = a + b + c;
        double halfPerimeter = perimeter / 2;
        this.area = Math.sqrt(halfPerimeter * (halfPerimeter - a) * (halfPerimeter - b) * (halfPerimeter - c));
    }

    public static Triangle of(String[] params) {
        double a = Double.parseDouble(params[0]);
        double b = Double.parseDouble(params[1]);
        double c = Double.parseDouble(params[2]);
        return new Triangle(a, b, c);
    }

    @Override
    public String getFormatedData() {
        return "Название: " + type + LINE_SEPARATOR +
                "Площадь: " + area + " кв. " + UNITS + LINE_SEPARATOR +
                "Периметр: " + perimeter + " " + UNITS + LINE_SEPARATOR;
    }

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
