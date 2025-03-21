package ru.shift.figurecharacteristics.figure;

import ru.shift.figurecharacteristics.input.FigureData;

public final class FigureFactory {

    private FigureFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static Figure createFigure(FigureData data) {
        return switch (data.getType()) {
            case CIRCLE -> new Circle(data);
            case RECTANGLE -> new Rectangle(data);
            case TRIANGLE -> new Triangle(data);
        };
    }
}
