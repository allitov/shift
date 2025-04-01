package ru.shift.figurecharacteristics.formatter;

import ru.shift.figurecharacteristics.figure.Figure;
import ru.shift.figurecharacteristics.figure.FigureType;
import ru.shift.figurecharacteristics.figure.Triangle;

public class TriangleFormatter implements FigureFormatter {

    @Override
    public FigureType getType() {
        return FigureType.TRIANGLE;
    }

    @Override
    public String format(Figure figure) {
        Triangle triangle = (Triangle) figure;
        double a = triangle.getA();
        double b = triangle.getB();
        double c = triangle.getC();

        return "Тип фигуры: " + triangle.getName() + LINE_SEPARATOR +
                "Площадь: " + DECIMAL_FORMAT.format(triangle.getArea()) + " кв. " + UNITS + LINE_SEPARATOR +
                "Периметр: " + DECIMAL_FORMAT.format(triangle.getPerimeter()) + SPACE + UNITS + LINE_SEPARATOR +
                "Сторона a: " + DECIMAL_FORMAT.format(a) + SPACE + UNITS + "; угол: " + DECIMAL_FORMAT.format(triangle.getSideToAngleMap().get(a)) + " радиан" + LINE_SEPARATOR +
                "Сторона b: " + DECIMAL_FORMAT.format(b) + SPACE + UNITS + "; угол: " + DECIMAL_FORMAT.format(triangle.getSideToAngleMap().get(b)) + " радиан" + LINE_SEPARATOR +
                "Сторона c: " + DECIMAL_FORMAT.format(c) + SPACE + UNITS + "; угол: " + DECIMAL_FORMAT.format(triangle.getSideToAngleMap().get(c)) + " радиан";
    }
}
