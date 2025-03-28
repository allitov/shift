package ru.shift.figurecharacteristics;

import java.text.DecimalFormat;

public abstract class Figure {

    protected static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");
    protected static final String LINE_SEPARATOR = System.lineSeparator();
    protected static final String DELIMITER = " ";
    protected static final String UNITS = "мм";

    protected String name;
    protected double area;
    protected double perimeter;

    public abstract String getFormattedData();

    public String getName() {
        return name;
    }

    public double getArea() {
        return area;
    }

    public double getPerimeter() {
        return perimeter;
    }
}
