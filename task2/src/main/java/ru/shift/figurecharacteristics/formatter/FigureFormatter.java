package ru.shift.figurecharacteristics.formatter;

import ru.shift.figurecharacteristics.figure.Figure;
import java.text.DecimalFormat;

public interface FigureFormatter<T extends Figure> {

    DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");
    String UNITS = "мм";
    String LINE_SEPARATOR = System.lineSeparator();

    String format(T figure);
}
