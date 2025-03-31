package ru.shift.figurecharacteristics.formatter;

import ru.shift.figurecharacteristics.figure.Circle;

public class CircleFormatter implements FigureFormatter<Circle> {

    @Override
    public String format(Circle circle) {
        return "Тип фигуры: " + circle.getName() + LINE_SEPARATOR +
                "Площадь: " + DECIMAL_FORMAT.format(circle.getArea()) + " кв. " + UNITS + LINE_SEPARATOR +
                "Периметр: " + DECIMAL_FORMAT.format(circle.getPerimeter()) + SPACE + UNITS + LINE_SEPARATOR +
                "Радиус: " + DECIMAL_FORMAT.format(circle.getRadius()) + SPACE + UNITS + LINE_SEPARATOR +
                "Диаметр: " + DECIMAL_FORMAT.format(circle.getDiameter()) + SPACE + UNITS;
    }
}
