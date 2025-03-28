package ru.shift.figurecharacteristics.formatter;

import ru.shift.figurecharacteristics.figure.Rectangle;

import java.text.DecimalFormat;

public class RectangleDataFormatter extends FigureDataFormatter<Rectangle> {

    public RectangleDataFormatter(DecimalFormat decimalFormat, String units) {
        super(decimalFormat, units);
    }

    @Override
    public String format(Rectangle rectangle) {
        return "Тип фигуры: " + rectangle.getName() + lineSeparator +
                "Площадь: " + decimalFormat.format(rectangle.getArea()) + " кв. " + units + lineSeparator +
                "Периметр: " + decimalFormat.format(rectangle.getPerimeter()) + " " + units + lineSeparator +
                "Диагональ: " + decimalFormat.format(rectangle.getDiagonal()) + " " + units + lineSeparator +
                "Длина: " + decimalFormat.format(rectangle.getLength()) + " " + units + lineSeparator +
                "Ширина: " + decimalFormat.format(rectangle.getWidth()) + " " + units;
    }
}
