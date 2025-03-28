package ru.shift.figurecharacteristics.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.shift.figurecharacteristics.figure.FigureType;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RectangleFactoryTest {

    @DisplayName("Test rectangle factory type")
    @Test
    void testRectangleFactoryType() {
        RectangleFactory factory = new RectangleFactory();

        assertEquals(FigureType.RECTANGLE, factory.getType());
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

        assertThrows(IllegalArgumentException.class, () -> new RectangleFactory().read(reader));
    }

    private String createInputLine(String params) {
        String lineSeparator = System.lineSeparator();

        return FigureType.RECTANGLE + lineSeparator + params;
    }
}
