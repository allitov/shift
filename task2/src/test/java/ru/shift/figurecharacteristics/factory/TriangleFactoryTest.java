package ru.shift.figurecharacteristics.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.shift.figurecharacteristics.figure.Figure;
import ru.shift.figurecharacteristics.figure.FigureType;
import ru.shift.figurecharacteristics.figure.Triangle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TriangleFactoryTest {

    @DisplayName("Test triangle factory type")
    @Test
    void testTriangleFactoryType() {
        TriangleFactory factory = new TriangleFactory();

        assertEquals(FigureType.TRIANGLE, factory.getType());
    }

    @DisplayName("Test triangle creation")
    @Test
    void testCircleCreation() throws IOException {
        String input = "2 4 5";
        BufferedReader reader = new BufferedReader(new StringReader(input));
        TriangleFactory factory = new TriangleFactory();

        Figure triangle = factory.read(reader);

        assertInstanceOf(Triangle.class, triangle);
    }

    @DisplayName("Test triangle input line validation")
    @ParameterizedTest(name = "[{index}] - validate line \"{0}\"")
    @CsvSource({
            "2 4 5 6",
            "red 2 4",
            "20 blue 40",
            "10 1 yellow"
    })
    void testTriangleInputLineValidation(String params) {
        String input = createInputLine(params);
        BufferedReader reader = new BufferedReader(new StringReader(input));

        assertThrows(IllegalArgumentException.class, () -> new TriangleFactory().read(reader));
    }

    private String createInputLine(String params) {
        String lineSeparator = System.lineSeparator();

        return FigureType.TRIANGLE + lineSeparator + params;
    }
}
