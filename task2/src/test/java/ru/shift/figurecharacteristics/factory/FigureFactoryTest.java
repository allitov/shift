package ru.shift.figurecharacteristics.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.shift.figurecharacteristics.figure.Circle;
import ru.shift.figurecharacteristics.figure.Figure;
import ru.shift.figurecharacteristics.figure.FigureType;
import ru.shift.figurecharacteristics.figure.Rectangle;
import ru.shift.figurecharacteristics.figure.Triangle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FigureFactoryTest {

    @DisplayName("Test circle factory creation")
    @Test
    void testCircleFactoryCreation() throws IOException {
        String input = createInput(FigureType.CIRCLE, "5");
        BufferedReader reader = new BufferedReader(new StringReader(input));

        Figure figure = FigureFactory.create(reader);

        assertInstanceOf(Circle.class, figure);
    }

    @DisplayName("Test rectangle factory creation")
    @Test
    void testRectangleFactoryCreation() throws IOException {
        String input = createInput(FigureType.RECTANGLE, "10 20");
        BufferedReader reader = new BufferedReader(new StringReader(input));

        Figure figure = FigureFactory.create(reader);

        assertInstanceOf(Rectangle.class, figure);
    }

    @DisplayName("Test triangle factory creation")
    @Test
    void testTriangleFactoryCreation() throws IOException {
        String input = createInput(FigureType.TRIANGLE, "2 4 5");
        BufferedReader reader = new BufferedReader(new StringReader(input));

        Figure figure = FigureFactory.create(reader);

        assertInstanceOf(Triangle.class, figure);
    }

    @DisplayName("Test invalid figure creation")
    @Test
    void testInvalidFigureCreation() {
        String input = "SQUARE";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        assertThrows(IllegalArgumentException.class, () -> FigureFactory.create(reader));
    }

    private String createInput(FigureType type, String params) {
        String lineSeparator = System.lineSeparator();

        return type + lineSeparator + params;
    }
}
