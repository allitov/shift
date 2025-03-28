package ru.shift.figurecharacteristics.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.shift.figurecharacteristics.figure.FigureType;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CircleFactoryTest {

    @DisplayName("Test circle factory type")
    @Test
    void testCircleFactoryType() {
        CircleFactory factory = new CircleFactory();

        assertEquals(FigureType.CIRCLE, factory.getType());
    }

    @DisplayName("Test circle input line validation")
    @ParameterizedTest(name = "[{index}] - validate params \"{0}\"")
    @ValueSource(strings = {"-b", "red", "1.2c34", "5 120"})
    void testCircleInputLineValidation(String params) {
        String input = createInputLine(params);
        BufferedReader reader = new BufferedReader(new StringReader(input));

        assertThrows(IllegalArgumentException.class, () -> new CircleFactory().read(reader));
    }

    private String createInputLine(String params) {
        String lineSeparator = System.lineSeparator();

        return FigureType.CIRCLE + lineSeparator + params;
    }
}
