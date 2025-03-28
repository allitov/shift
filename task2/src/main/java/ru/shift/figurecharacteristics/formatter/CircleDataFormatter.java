package ru.shift.figurecharacteristics.formatter;

import ru.shift.figurecharacteristics.figure.Circle;

import java.text.DecimalFormat;

public class CircleDataFormatter {

    private final DecimalFormat decimalFormat;
    private final String units;
    private final String lineSeparator;

    public CircleDataFormatter(DecimalFormat decimalFormat, String units) {
        this.decimalFormat = decimalFormat;
        this.units = units;
        this.lineSeparator = System.lineSeparator();
    }

    public String format(Circle circle) {
        return "Тип фигуры: " + circle.getName() + lineSeparator +
                "Площадь: " + decimalFormat.format(circle.getArea()) + " кв. " + units + lineSeparator +
                "Периметр: " + decimalFormat.format(circle.getPerimeter()) + " " + units + lineSeparator +
                "Радиус: " + decimalFormat.format(circle.getRadius()) + " " + units + lineSeparator +
                "Диаметр: " + decimalFormat.format(circle.getDiameter()) + " " + units;
    }
}
