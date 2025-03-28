package ru.shift.figurecharacteristics.formatter;

import ru.shift.figurecharacteristics.figure.Triangle;

public class TriangleFormatter implements FigureFormatter<Triangle> {

    @Override
    public String format(Triangle triangle) {
        double a = triangle.getA();
        double b = triangle.getB();
        double c = triangle.getC();

        return "Тип фигуры: " + triangle.getName() + LINE_SEPARATOR +
                "Площадь: " + DECIMAL_FORMAT.format(triangle.getArea()) + " кв. " + UNITS + LINE_SEPARATOR +
                "Периметр: " + DECIMAL_FORMAT.format(triangle.getPerimeter()) + " " + UNITS + LINE_SEPARATOR +
                "Сторона a: " + DECIMAL_FORMAT.format(a) + " " + UNITS + "; угол: " + DECIMAL_FORMAT.format(triangle.getSideToAngleMap().get(a)) + " градусов" + LINE_SEPARATOR +
                "Сторона b: " + DECIMAL_FORMAT.format(b) + " " + UNITS + "; угол: " + DECIMAL_FORMAT.format(triangle.getSideToAngleMap().get(b)) + " градусов" + LINE_SEPARATOR +
                "Сторона c: " + DECIMAL_FORMAT.format(c) + " " + UNITS + "; угол: " + DECIMAL_FORMAT.format(triangle.getSideToAngleMap().get(c)) + " градусов";
    }
}
