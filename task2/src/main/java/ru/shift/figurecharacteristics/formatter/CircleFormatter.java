package ru.shift.figurecharacteristics.formatter;

import ru.shift.figurecharacteristics.figure.Circle;
import ru.shift.figurecharacteristics.figure.Figure;
import ru.shift.figurecharacteristics.figure.FigureType;

public class CircleFormatter implements FigureFormatter {

    @Override
    public FigureType getType() {
        return FigureType.CIRCLE;
    }

    @Override
    public String format(Figure figure) {
        Circle circle = (Circle) figure;

        return "Тип фигуры: " + circle.getName() + LINE_SEPARATOR +
                "Площадь: " + DECIMAL_FORMAT.format(circle.getArea()) + " кв. " + UNITS + LINE_SEPARATOR +
                "Периметр: " + DECIMAL_FORMAT.format(circle.getPerimeter()) + SPACE + UNITS + LINE_SEPARATOR +
                "Радиус: " + DECIMAL_FORMAT.format(circle.getRadius()) + SPACE + UNITS + LINE_SEPARATOR +
                "Диаметр: " + DECIMAL_FORMAT.format(circle.getDiameter()) + SPACE + UNITS;
    }
}
