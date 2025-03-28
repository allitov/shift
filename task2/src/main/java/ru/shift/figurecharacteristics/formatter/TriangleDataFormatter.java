package ru.shift.figurecharacteristics.formatter;

import ru.shift.figurecharacteristics.figure.Triangle;

import java.text.DecimalFormat;

public class TriangleDataFormatter extends FigureDataFormatter<Triangle> {

    public TriangleDataFormatter(DecimalFormat decimalFormat, String units) {
        super(decimalFormat, units);
    }

    @Override
    public String format(Triangle triangle) {
        double a = triangle.getA();
        double b = triangle.getB();
        double c = triangle.getC();

        return "Тип фигуры: " + triangle.getName() + lineSeparator +
                "Площадь: " + decimalFormat.format(triangle.getArea()) + " кв. " + units + lineSeparator +
                "Периметр: " + decimalFormat.format(triangle.getPerimeter()) + " " + units + lineSeparator +
                "Сторона a: " + decimalFormat.format(a) + " " + units + "; угол: " + decimalFormat.format(triangle.getSideToAngleMap().get(a)) + " градусов" + lineSeparator +
                "Сторона b: " + decimalFormat.format(b) + " " + units + "; угол: " + decimalFormat.format(triangle.getSideToAngleMap().get(b)) + " градусов" + lineSeparator +
                "Сторона c: " + decimalFormat.format(c) + " " + units + "; угол: " + decimalFormat.format(triangle.getSideToAngleMap().get(c)) + " градусов";
    }
}
