package ru.shift.figurecharacteristics;

import java.io.BufferedReader;
import java.io.IOException;

public class Rectangle extends Figure {

    private double diagonal;
    private double length;
    private double width;

    public static Rectangle readRectangle(BufferedReader reader) throws IOException {
        String[] params = reader.readLine().split(DELIMITER);
        if (params.length != 2) {
            throw new IllegalArgumentException("Rectangle expects 2 parameters. Got " + params.length);
        }

        double a, b;
        try {
            a = Double.parseDouble(params[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Rectangle side expects a number. Got a = " + params[0]);
        }
        try {
            b = Double.parseDouble(params[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Rectangle side expects a number. Got b = " + params[1]);
        }

        return new Rectangle(a, b);
    }

    private Rectangle(double a, double b) {
        validate(a, b);
        this.name = "Прямоугольник";
        this.area = a * b;
        this.perimeter = (a + b) * 2;
        this.diagonal = Math.sqrt((a * a) + (b * b));
        this.length = Math.max(a, b);
        this.width = Math.min(a, b);
    }

    @Override
    public String getFormattedData() {
        return "Тип фигуры: " + name + LINE_SEPARATOR +
                "Площадь: " + DECIMAL_FORMAT.format(area) + " кв. " + UNITS + LINE_SEPARATOR +
                "Периметр: " + DECIMAL_FORMAT.format(perimeter) + " " + UNITS + LINE_SEPARATOR +
                "Диагональ: " + DECIMAL_FORMAT.format(diagonal) + " " + UNITS + LINE_SEPARATOR +
                "Длина: " + DECIMAL_FORMAT.format(length) + " " + UNITS + LINE_SEPARATOR +
                "Ширина: " + DECIMAL_FORMAT.format(width) + " " + UNITS + LINE_SEPARATOR;
    }

    private void validate(double a, double b) {
        if (a <= 0) {
            throw new IllegalArgumentException("Rectangle side must be greater than 0. Got " + a);
        }
        if (b <= 0) {
            throw new IllegalArgumentException("Rectangle side must be greater than 0. Got " + b);
        }
    }

    public double getDiagonal() {
        return diagonal;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }
}
