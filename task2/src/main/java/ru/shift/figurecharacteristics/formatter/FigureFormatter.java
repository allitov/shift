package ru.shift.figurecharacteristics.formatter;

import ru.shift.figurecharacteristics.figure.Circle;
import ru.shift.figurecharacteristics.figure.Figure;
import ru.shift.figurecharacteristics.figure.Rectangle;
import ru.shift.figurecharacteristics.figure.Triangle;

import java.text.DecimalFormat;

public interface FigureFormatter<T extends Figure> {

    DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");
    String UNITS = "мм";
    String LINE_SEPARATOR = System.lineSeparator();
    String SPACE = " ";

    String format(T figure);

    static String createFormattedString(Figure figure) {
        return switch (figure.getType()) {
            case CIRCLE -> new CircleFormatter().format((Circle) figure);
            case RECTANGLE -> new RectangleFormatter().format((Rectangle) figure);
            case TRIANGLE -> new TriangleFormatter().format((Triangle) figure);
        };
    }
}
