package ru.shift.figurecharacteristics.formatter;

import ru.shift.figurecharacteristics.figure.Rectangle;

public class RectangleFormatter implements FigureFormatter<Rectangle> {

    @Override
    public String format(Rectangle rectangle) {
        return "Тип фигуры: " + rectangle.getName() + LINE_SEPARATOR +
                "Площадь: " + DECIMAL_FORMAT.format(rectangle.getArea()) + " кв. " + UNITS + LINE_SEPARATOR +
                "Периметр: " + DECIMAL_FORMAT.format(rectangle.getPerimeter()) + " " + UNITS + LINE_SEPARATOR +
                "Диагональ: " + DECIMAL_FORMAT.format(rectangle.getDiagonal()) + " " + UNITS + LINE_SEPARATOR +
                "Длина: " + DECIMAL_FORMAT.format(rectangle.getLength()) + " " + UNITS + LINE_SEPARATOR +
                "Ширина: " + DECIMAL_FORMAT.format(rectangle.getWidth()) + " " + UNITS;
    }
}
