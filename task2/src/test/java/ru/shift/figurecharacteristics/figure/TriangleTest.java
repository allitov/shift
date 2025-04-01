package ru.shift.figurecharacteristics.figure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TriangleTest {

    @DisplayName("Test triangle name initialization")
    @Test
    void testTriangleNameInitialization() {
        Triangle triangle = new Triangle(2, 4, 5);

        assertEquals("Треугольник", triangle.getName());
    }

    @DisplayName("Test triangle 'a' side initialization")
    @Test
    void testTriangleASideInitialization() {
        Triangle triangle = new Triangle(2, 4, 5);

        assertEquals(2, triangle.getA());
    }

    @DisplayName("Test triangle 'b' side initialization")
    @Test
    void testTriangleBSideInitialization() {
        Triangle triangle = new Triangle(2, 4, 5);

        assertEquals(4, triangle.getB());
    }

    @DisplayName("Test triangle 'c' side initialization")
    @Test
    void testTriangleCSideInitialization() {
        Triangle triangle = new Triangle(2, 4, 5);

        assertEquals(5, triangle.getC());
    }

    @DisplayName("Test triangle area calculation")
    @ParameterizedTest(name = "[{index}] - triangle with a = {0}, b = {1}, c = {2}")
    @CsvSource({
            "2, 4, 5",
            "10, 20, 25"
    })
    void testTriangleAreaCalculation(double a, double b, double c) {
        double area = calculateArea(a, b, c);

        Triangle triangle = new Triangle(a, b, c);

        assertEquals(area, triangle.getArea());
    }

    @DisplayName("Test triangle perimeter calculation")
    @ParameterizedTest(name = "[{index}] - triangle with a = {0}, b = {1}, c = {2}")
    @CsvSource({
            "2, 4, 5",
            "10, 20, 25"
    })
    void testTrianglePerimeterCalculation(double a, double b, double c) {
        double perimeter = calculatePerimeter(a, b, c);

        Triangle triangle = new Triangle(a, b, c);

        assertEquals(perimeter, triangle.getPerimeter());
    }

    @DisplayName("Test triangle angles calculation")
    @ParameterizedTest(name = "[{index}] - triangle with a = {0}, b = {1}, c = {2}")
    @CsvSource({
            "2, 4, 5",
            "10, 20, 25"
    })
    void testTriangleAnglesCalculation(double a, double b, double c) {
        Map<Double, Double> expected = calculateSideToAngle(a, b, c);

        Triangle triangle = new Triangle(a, b, c);

        assertEquals(expected, triangle.getSideToAngleMap());
    }

    @DisplayName("Test triangle sides validation")
    @ParameterizedTest(name = "[{index}] - validate sides a = {0}, b = {1}, c = {2}")
    @CsvSource({
            "-2, 4, 5",
            "2, -4, 5",
            "2, 4, -5",
            "0, 4, 5",
            "2, 0, 5",
            "2, 4, 0",
            "120, 4, 5",
            "2, 120, 5",
            "2, 4, 120"
    })
    void testTriangleSidesValidation(double a, double b, double c) {
        assertThrows(IllegalArgumentException.class, () -> new Triangle(a, b, c));
    }

    private double calculatePerimeter(double a, double b, double c) {
        return a + b + c;
    }

    private double calculateArea(double a, double b, double c) {
        double p = calculatePerimeter(a, b, c) / 2;

        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    private Map<Double, Double> calculateSideToAngle(double a, double b, double c) {
        double angleA = Math.acos((b * b + c * c - a * a) / (2 * b * c));
        double angleB = Math.acos((a * a + c * c - b * b) / (2 * a * c));
        double angleC = Math.acos((a * a + b * b - c * c) / (2 * a * b));

        return Map.of(a, angleA, b, angleB, c, angleC);
    }
}
