package ru.shift.figurecharacteristics.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.shift.figurecharacteristics.figure.Figure;
import ru.shift.figurecharacteristics.figure.Triangle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TriangleFactoryTest {

    @DisplayName("Test triangle factory creation")
    @Test
    void testTriangleFactoryCreation() throws IOException {
        String input = createInputLine("2 4 5");
        BufferedReader reader = new BufferedReader(new StringReader(input));

        Figure triangle = FigureFactory.create(reader);

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

        assertThrows(IllegalArgumentException.class, () -> FigureFactory.create(reader));
    }

    private String createInputLine(String params) {
        String lineSeparator = System.lineSeparator();

        return "TRIANGLE" + lineSeparator + params;
    }
}
