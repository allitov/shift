package ru.shift.figurecharacteristics.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.shift.figurecharacteristics.figure.Figure;
import ru.shift.figurecharacteristics.figure.Rectangle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RectangleFactoryTest {

    @DisplayName("Test rectangle factory creation")
    @Test
    void testRectangleFactoryCreation() throws IOException {
        String input = createInputLine("10 20");
        BufferedReader reader = new BufferedReader(new StringReader(input));

        Figure rectangle = FigureFactory.create(reader);

        assertInstanceOf(Rectangle.class, rectangle);
    }

    @DisplayName("Test rectangle input line validation")
    @ParameterizedTest(name = "[{index}] - validate line \"{0}\"")
    @CsvSource({
            "10 20 30",
            "red 20",
            "20 blue",
            "10.34c43 1"
    })
    void testRectangleInputLineValidation(String params) {
        String input = createInputLine(params);
        BufferedReader reader = new BufferedReader(new StringReader(input));

        assertThrows(IllegalArgumentException.class, () -> FigureFactory.create(reader));
    }

    private String createInputLine(String params) {
        String lineSeparator = System.lineSeparator();

        return "RECTANGLE" + lineSeparator + params;
    }
}
