package ru.shift.figurecharacteristics.figure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RectangleTest {

    @DisplayName("Test rectangle name initialization")
    @Test
    void testRectangleNameInitialization() {
        Rectangle rectangle = new Rectangle(5, 10);

        assertEquals("Прямоугольник", rectangle.getName());
    }

    @DisplayName("Test rectangle area calculation")
    @ParameterizedTest(name = "[{index}] - rectangle with a = {0}, b = {1}")
    @CsvSource({
            "10, 20",
            "1.15, 10"
    })
    void testRectangleAreaCalculation(double a, double b) {
        double area = calculateArea(a, b);

        Rectangle rectangle = new Rectangle(a, b);

        assertEquals(area, rectangle.getArea());
    }

    @DisplayName("Test rectangle perimeter calculation")
    @ParameterizedTest(name = "[{index}] - rectangle with a = {0}, b = {1}")
    @CsvSource({
            "10, 20",
            "1.15, 10"
    })
    void testRectanglePerimeterCalculation(double a, double b) {
        double perimeter = calculatePerimeter(a, b);

        Rectangle rectangle = new Rectangle(a, b);

        assertEquals(perimeter, rectangle.getPerimeter());
    }

    @DisplayName("Test rectangle diagonal calculation")
    @ParameterizedTest(name = "[{index}] - rectangle with a = {0}, b = {1}")
    @CsvSource({
            "10, 20",
            "1.15, 10"
    })
    void testRectangleDiagonalCalculation(double a, double b) {
        double diagonal = calculateDiagonal(a, b);

        Rectangle rectangle = new Rectangle(a, b);

        assertEquals(diagonal, rectangle.getDiagonal());
    }

    @DisplayName("Test rectangle length calculation")
    @ParameterizedTest(name = "[{index}] - rectangle with a = {0}, b = {1}")
    @CsvSource({
            "10, 20",
            "1.15, 10"
    })
    void testRectangleLengthCalculation(double a, double b) {
        double length = calculateLength(a, b);

        Rectangle rectangle = new Rectangle(a, b);

        assertEquals(length, rectangle.getLength());
    }

    @DisplayName("Test rectangle width calculation")
    @ParameterizedTest(name = "[{index}] - rectangle with a = {0}, b = {1}")
    @CsvSource({
            "10, 20",
            "1.15, 10"
    })
    void testRectangleWidthCalculation(double a, double b) {
        double width = calculateWidth(a, b);

        Rectangle rectangle = new Rectangle(a, b);

        assertEquals(width, rectangle.getWidth());
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
        assertThrows(IllegalArgumentException.class, () -> new Rectangle(a, b));
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
