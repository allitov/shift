package ru.shift.figurecharacteristics.figure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RectangleTest {

    private static final String DELIMITER = " ";

    @DisplayName("Test rectangle factory creation")
    @Test
    void testRectangleFactoryCreation() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader("5 10"));

        Rectangle rectangle = Rectangle.readRectangle(reader);

        assertInstanceOf(Rectangle.class, rectangle);
    }

    @DisplayName("Test rectangle name initialization")
    @Test
    void testRectangleNameInitialization() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader("5 10"));

        Rectangle rectangle = Rectangle.readRectangle(reader);

        assertEquals("Прямоугольник", rectangle.getName());
    }

    @DisplayName("Test rectangle area calculation")
    @ParameterizedTest(name = "[{index}] - rectangle with a = {0}, b = {1}")
    @CsvSource({
            "10, 20",
            "1.15, 10"
    })
    void testRectangleAreaCalculation(double a, double b) throws IOException {
        double area = calculateArea(a, b);
        String line = a + DELIMITER + b;
        BufferedReader reader = new BufferedReader(new StringReader(line));

        Rectangle rectangle = Rectangle.readRectangle(reader);

        assertEquals(area, rectangle.getArea());
    }

    @DisplayName("Test rectangle perimeter calculation")
    @ParameterizedTest(name = "[{index}] - rectangle with a = {0}, b = {1}")
    @CsvSource({
            "10, 20",
            "1.15, 10"
    })
    void testRectanglePerimeterCalculation(double a, double b) throws IOException {
        double perimeter = calculatePerimeter(a, b);
        String line = a + DELIMITER + b;
        BufferedReader reader = new BufferedReader(new StringReader(line));

        Rectangle rectangle = Rectangle.readRectangle(reader);

        assertEquals(perimeter, rectangle.getPerimeter());
    }

    @DisplayName("Test rectangle diagonal calculation")
    @ParameterizedTest(name = "[{index}] - rectangle with a = {0}, b = {1}")
    @CsvSource({
            "10, 20",
            "1.15, 10"
    })
    void testRectangleDiagonalCalculation(double a, double b) throws IOException {
        double diagonal = calculateDiagonal(a, b);
        String line = a + DELIMITER + b;
        BufferedReader reader = new BufferedReader(new StringReader(line));

        Rectangle rectangle = Rectangle.readRectangle(reader);

        assertEquals(diagonal, rectangle.getDiagonal());
    }

    @DisplayName("Test rectangle length calculation")
    @ParameterizedTest(name = "[{index}] - rectangle with a = {0}, b = {1}")
    @CsvSource({
            "10, 20",
            "1.15, 10"
    })
    void testRectangleLengthCalculation(double a, double b) throws IOException {
        double length = calculateLength(a, b);
        String line = a + DELIMITER + b;
        BufferedReader reader = new BufferedReader(new StringReader(line));

        Rectangle rectangle = Rectangle.readRectangle(reader);

        assertEquals(length, rectangle.getLength());
    }

    @DisplayName("Test rectangle width calculation")
    @ParameterizedTest(name = "[{index}] - rectangle with a = {0}, b = {1}")
    @CsvSource({
            "10, 20",
            "1.15, 10"
    })
    void testRectangleWidthCalculation(double a, double b) throws IOException {
        double width = calculateWidth(a, b);
        String line = a + DELIMITER + b;
        BufferedReader reader = new BufferedReader(new StringReader(line));

        Rectangle rectangle = Rectangle.readRectangle(reader);

        assertEquals(width, rectangle.getWidth());
    }

    @DisplayName("Test rectangle input line validation")
    @ParameterizedTest(name = "[{index}] - validate line \"{0}\"")
    @CsvSource({
            "10 20 30",
            "red 20",
            "20 blue",
            "10.34c43 1"
    })
    void testRectangleInputLineValidation(String line) {
        BufferedReader reader = new BufferedReader(new StringReader(line));

        assertThrows(IllegalArgumentException.class, () -> Rectangle.readRectangle(reader));
    }

    @DisplayName("Test rectangle sides validation")
    @ParameterizedTest(name = "[{index}] - validate sides a = {0}, b = {1}")
    @CsvSource({
            "-1, 10",
            "10, -1",
            "20, 0",
            "-10, -20",
            "0, 10"
    })
    void testRectangleSidesValidation(double a, double b) {
        String line = a + DELIMITER + b;
        BufferedReader reader = new BufferedReader(new StringReader(line));

        assertThrows(IllegalArgumentException.class, () -> Rectangle.readRectangle(reader));
    }

    private double calculateArea(double a, double b) {
        return a * b;
    }

    private double calculatePerimeter(double a, double b) {
        return (a + b) * 2;
    }

    private double calculateDiagonal(double a, double b) {
        return Math.sqrt((a * a) + (b * b));
    }

    private double calculateLength(double a, double b) {
        return Math.max(a, b);
    }

    private double calculateWidth(double a, double b) {
        return Math.min(a, b);
    }
}
