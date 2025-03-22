package ru.shift.figurecharacteristics.figure.entity;

import java.text.DecimalFormat;

public abstract class Figure {

    protected static final String LINE_SEPARATOR = System.lineSeparator();
    protected static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");
    protected static final String UNITS = "мм";

    public abstract String getFormatedData();
}
