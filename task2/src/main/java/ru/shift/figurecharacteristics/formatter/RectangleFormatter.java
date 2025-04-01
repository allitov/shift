package ru.shift.figurecharacteristics.formatter;

import ru.shift.figurecharacteristics.figure.Figure;
import ru.shift.figurecharacteristics.figure.FigureType;
import ru.shift.figurecharacteristics.figure.Rectangle;

public class RectangleFormatter implements FigureFormatter {

    @Override
    public FigureType getType() {
        return FigureType.RECTANGLE;
    }

    @Override
    public String format(Figure figure) {
        Rectangle rectangle = (Rectangle) figure;

        return "Тип фигуры: " + rectangle.getName() + LINE_SEPARATOR +
                "Площадь: " + DECIMAL_FORMAT.format(rectangle.getArea()) + " кв. " + UNITS + LINE_SEPARATOR +
                "Периметр: " + DECIMAL_FORMAT.format(rectangle.getPerimeter()) + SPACE + UNITS + LINE_SEPARATOR +
                "Диагональ: " + DECIMAL_FORMAT.format(rectangle.getDiagonal()) + SPACE + UNITS + LINE_SEPARATOR +
                "Длина: " + DECIMAL_FORMAT.format(rectangle.getLength()) + SPACE + UNITS + LINE_SEPARATOR +
                "Ширина: " + DECIMAL_FORMAT.format(rectangle.getWidth()) + SPACE + UNITS;
    }
}
