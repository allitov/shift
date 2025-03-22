package ru.shift.figurecharacteristics.figure.factory;

import ru.shift.figurecharacteristics.figure.entity.Circle;
import ru.shift.figurecharacteristics.figure.entity.Figure;
import ru.shift.figurecharacteristics.figure.entity.Rectangle;
import ru.shift.figurecharacteristics.figure.entity.Triangle;
import ru.shift.figurecharacteristics.input.FigureData;

public final class FigureFactory {

    private FigureFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static Figure createFigure(FigureData data) {
        return switch (data.getType()) {
            case CIRCLE -> Circle.of(data.getParams());
            case RECTANGLE -> Rectangle.of(data.getParams());
            case TRIANGLE -> Triangle.of(data.getParams());
        };
    }
}
