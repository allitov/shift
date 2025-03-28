package ru.shift.figurecharacteristics.figure;

public enum FigureType {

    CIRCLE("Круг"),
    RECTANGLE("Прямоугольник"),
    TRIANGLE("Треугольник");

    private final String name;

    FigureType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
