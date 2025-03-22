package ru.shift.figurecharacteristics.figure.entity;

public class Rectangle extends Figure {

    private final FigureType type;
    private final double area;
    private final double perimeter;
    private final double diagonal;
    private final double length;
    private final double width;

    private Rectangle(double a, double b) {
        this.type = FigureType.RECTANGLE;
        this.area = a * b;
        this.perimeter = (a + b) * 2;
        this.diagonal = Math.sqrt(a * a + b * b);
        this.length = Math.max(a, b);
        this.width = Math.min(a, b);
    }

    public static Rectangle of(String[] params) {
        double a = Double.parseDouble(params[0]);
        double b = Double.parseDouble(params[1]);
        return new Rectangle(a, b);
    }

    @Override
    public String getFormatedData() {
        return "Название: " + type + LINE_SEPARATOR +
                "Площадь: " + area + " кв. " + UNITS + LINE_SEPARATOR +
                "Периметр: " + perimeter + " " + UNITS + LINE_SEPARATOR +
                "Диагональ: " + diagonal + " " + UNITS + LINE_SEPARATOR +
                "Длина: " + length + " " + UNITS + LINE_SEPARATOR +
                "Ширина: " + width + " " + UNITS + LINE_SEPARATOR;
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
