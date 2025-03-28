package ru.shift.figurecharacteristics.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.shift.figurecharacteristics.figure.Circle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CircleFactoryTest {

    @DisplayName("Test circle factory creation")
    @Test
    void testCircleFactoryCreation() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader("5"));

        Circle circle = CircleFactory.create(reader);

        assertInstanceOf(Circle.class, circle);
    }

    @DisplayName("Test circle input line validation")
    @ParameterizedTest(name = "[{index}] - validate line \"{0}\"")
    @ValueSource(strings = {"-b", "red", "1.2c34", "5 120"})
    void testCircleInputLineValidation(String line) {
        BufferedReader reader = new BufferedReader(new StringReader(line));

        assertThrows(IllegalArgumentException.class, () -> CircleFactory.create(reader));
    }
}
