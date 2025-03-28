package ru.shift.figurecharacteristics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CircleTest {

    @DisplayName("Test circle factory creation")
    @Test
    void testCircleFactoryCreation() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader("5"));

        Circle circle = Circle.readCircle(reader);

        assertInstanceOf(Circle.class, circle);
    }

    @DisplayName("Test circle radius calculation")
    @ParameterizedTest(name = "[{index}] - circle with radius {0}")
    @ValueSource(doubles = {5, 10})
    void testCircleRadiusCalculation(double radius) throws IOException {
        String line = String.valueOf(radius);
        BufferedReader reader = new BufferedReader(new StringReader(line));

        Circle circle = Circle.readCircle(reader);

        assertEquals(radius, circle.getRadius());
    }

    @DisplayName("Test circle name initialization")
    @Test
    void testCircleNameInitialization() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader("5"));

        Circle circle = Circle.readCircle(reader);

        assertEquals("Круг", circle.getName());
    }

    @DisplayName("Test circle diameter calculation")
    @ParameterizedTest(name = "[{index}] - circle with radius {0}")
    @ValueSource(doubles = {5, 10})
    void testCircleDiameterCalculation(double radius) throws IOException {
        double diameter = calculateDiameter(radius);
        String line = String.valueOf(radius);
        BufferedReader reader = new BufferedReader(new StringReader(line));

        Circle circle = Circle.readCircle(reader);

        assertEquals(diameter, circle.getDiameter());
    }

    @DisplayName("Test circle area calculation")
    @ParameterizedTest(name = "[{index}] - circle with radius {0}")
    @ValueSource(doubles = {5, 10})
    void testCircleAreaCalculation(double radius) throws IOException {
        double area = calculateArea(radius);
        String line = String.valueOf(radius);
        BufferedReader reader = new BufferedReader(new StringReader(line));

        Circle circle = Circle.readCircle(reader);

        assertEquals(area, circle.getArea());
    }

    @DisplayName("Test circle perimeter calculation")
    @ParameterizedTest(name = "[{index}] - circle with radius {0}")
    @ValueSource(doubles = {5, 10})
    void testCirclePerimeterCalculation(double radius) throws IOException {
        double perimeter = calculatePerimeter(radius);
        String line = String.valueOf(radius);
        BufferedReader reader = new BufferedReader(new StringReader(line));

        Circle circle = Circle.readCircle(reader);

        assertEquals(perimeter, circle.getPerimeter());
    }

    @DisplayName("Test circle input line validation")
    @ParameterizedTest(name = "[{index}] - validate line \"{0}\"")
    @ValueSource(strings = {"-b", "red", "1.2c34", "5 120"})
    void testCircleInputLineValidation(String line) {
        BufferedReader reader = new BufferedReader(new StringReader(line));

        assertThrows(IllegalArgumentException.class, () -> Circle.readCircle(reader));
    }

    @DisplayName("Test circle radius validation")
    @ParameterizedTest(name = "[{index}] - validate radius {0}")
    @ValueSource(doubles = {-1, 0, -1e-10})
    void testCircleRadiusValidation(double radius) {
        String line = String.valueOf(radius);
        BufferedReader reader = new BufferedReader(new StringReader(line));

        assertThrows(IllegalArgumentException.class, () -> Circle.readCircle(reader));
    }

    @DisplayName("Test circle data formatting")
    @Test
    void testCircleDataFormatting() throws IOException {
        double radius = 5;
        double area = calculateArea(radius);
        double perimeter = calculatePerimeter(radius);
        double diameter = calculateDiameter(radius);
        DecimalFormat df = new DecimalFormat("#.##");
        String lineSeparator = System.lineSeparator();
        String expected = "Тип фигуры: Круг" + lineSeparator +
                "Площадь: " + df.format(area) + " кв. мм" + lineSeparator +
                "Периметр: " + df.format(perimeter) + " мм" + lineSeparator +
                "Радиус: " + df.format(radius) + " мм" + lineSeparator +
                "Диаметр: " + df.format(diameter) + " мм" + lineSeparator;
        BufferedReader reader = new BufferedReader(new StringReader(String.valueOf(radius)));

        Circle circle = Circle.readCircle(reader);

        assertEquals(expected, circle.getFormattedData());
    }

    private double calculateArea(double radius) {
        return Math.PI * radius * radius;
    }

    private double calculatePerimeter(double radius) {
        return 2 * Math.PI * radius;
    }

    private double calculateDiameter(double radius) {
        return 2 * radius;
    }
}
