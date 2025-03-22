package ru.shift.figurecharacteristics.figure.entity;

public class Circle extends Figure {

    private final FigureType type;
    private final double area;
    private final double perimeter;
    private final double radius;
    private final double diameter;

    private Circle(double radius) {
        this.type = FigureType.CIRCLE;
        this.area = Math.PI * radius * radius;
        this.perimeter = 2 * Math.PI * radius;
        this.radius = radius;
        this.diameter = 2 * radius;
    }

    public static Circle of(String[] params) {
        double radius = Double.parseDouble(params[0]);
        return new Circle(radius);
    }
    @Override
    public String getFormatedData() {
        return "Тип фигуры: " + type + LINE_SEPARATOR +
                "Площадь: " + DECIMAL_FORMAT.format(area) + " кв. " + UNITS + LINE_SEPARATOR +
                "Периметр: " + DECIMAL_FORMAT.format(perimeter) + " " + UNITS + LINE_SEPARATOR +
                "Радиус: " + DECIMAL_FORMAT.format(radius) + " " + UNITS + LINE_SEPARATOR +
                "Диаметр: " + DECIMAL_FORMAT.format(diameter) + " " + UNITS + LINE_SEPARATOR;
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

    public double getRadius() {
        return radius;
    }

    public double getDiameter() {
        return diameter;
    }
}
