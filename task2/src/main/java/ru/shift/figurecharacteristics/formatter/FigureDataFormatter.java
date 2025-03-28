package ru.shift.figurecharacteristics.formatter;

import ru.shift.figurecharacteristics.figure.Figure;

import java.text.DecimalFormat;
import java.util.Objects;

public abstract class FigureDataFormatter<T extends Figure> {

    protected final DecimalFormat decimalFormat;
    protected final String units;
    protected final String lineSeparator;

    protected FigureDataFormatter(DecimalFormat decimalFormat, String units) {
        Objects.requireNonNull(decimalFormat);
        Objects.requireNonNull(units);
        this.decimalFormat = decimalFormat;
        this.units = units;
        this.lineSeparator = System.lineSeparator();
    }

    public abstract String format(T figure);
}
