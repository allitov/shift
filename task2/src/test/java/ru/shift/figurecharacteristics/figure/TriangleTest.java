package ru.shift.figurecharacteristics.figure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TriangleTest {

    private static final String DELIMITER = " ";

    @DisplayName("Test triangle factory creation")
    @Test
    void testTriangleFactoryCreation() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader("2 4 5"));

        Triangle triangle = Triangle.readTriangle(reader);

        assertInstanceOf(Triangle.class, triangle);
    }

    @DisplayName("Test triangle name initialization")
    @Test
    void testTriangleNameInitialization() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader("2 4 5"));

        Triangle triangle = Triangle.readTriangle(reader);

        assertEquals("Треугольник", triangle.getName());
    }

    @DisplayName("Test triangle 'a' side initialization")
    @Test
    void testTriangleASideInitialization() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader("2 4 5"));

        Triangle triangle = Triangle.readTriangle(reader);

        assertEquals(2, triangle.getA());
    }

    @DisplayName("Test triangle 'b' side initialization")
    @Test
    void testTriangleBSideInitialization() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader("2 4 5"));

        Triangle triangle = Triangle.readTriangle(reader);

        assertEquals(4, triangle.getB());
    }

    @DisplayName("Test triangle 'c' side initialization")
    @Test
    void testTriangleCSideInitialization() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader("2 4 5"));

        Triangle triangle = Triangle.readTriangle(reader);

        assertEquals(5, triangle.getC());
    }

    @DisplayName("Test triangle area calculation")
    @ParameterizedTest(name = "[{index}] - triangle with a = {0}, b = {1}, c = {2}")
    @CsvSource({
            "2, 4, 5",
            "10, 20, 25"
    })
    void testTriangleAreaCalculation(double a, double b, double c) throws IOException {
        double area = calculateArea(a, b, c);
        String line = a + DELIMITER + b + DELIMITER + c;
        BufferedReader reader = new BufferedReader(new StringReader(line));

        Triangle triangle = Triangle.readTriangle(reader);

        assertEquals(area, triangle.getArea());
    }

    @DisplayName("Test triangle perimeter calculation")
    @ParameterizedTest(name = "[{index}] - triangle with a = {0}, b = {1}, c = {2}")
    @CsvSource({
            "2, 4, 5",
            "10, 20, 25"
    })
    void testTrianglePerimeterCalculation(double a, double b, double c) throws IOException {
        double perimeter = calculatePerimeter(a, b, c);
        String line = a + DELIMITER + b + DELIMITER + c;
        BufferedReader reader = new BufferedReader(new StringReader(line));

        Triangle triangle = Triangle.readTriangle(reader);

        assertEquals(perimeter, triangle.getPerimeter());
    }

    @DisplayName("Test triangle angles calculation")
    @ParameterizedTest(name = "[{index}] - triangle with a = {0}, b = {1}, c = {2}")
    @CsvSource({
            "2, 4, 5",
            "10, 20, 25"
    })
    void testTriangleAnglesCalculation(double a, double b, double c) throws IOException {
        Map<Double, Double> expected = calculateSideToAngle(a, b, c);
        String line = a + DELIMITER + b + DELIMITER + c;
        BufferedReader reader = new BufferedReader(new StringReader(line));

        Triangle triangle = Triangle.readTriangle(reader);

        assertEquals(expected, triangle.getSideToAngleMap());
    }

    @DisplayName("Test triangle input line validation")
    @ParameterizedTest(name = "[{index}] - validate line \"{0}\"")
    @CsvSource({
            "2 4 5 6",
            "red 2 4",
            "20 blue 40",
            "10 1 yellow"
    })
    void testTriangleInputLineValidation(String line) {
        BufferedReader reader = new BufferedReader(new StringReader(line));

        assertThrows(IllegalArgumentException.class, () -> Triangle.readTriangle(reader));
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
        String line = a + DELIMITER + b + DELIMITER + c;
        BufferedReader reader = new BufferedReader(new StringReader(line));

        assertThrows(IllegalArgumentException.class, () -> Triangle.readTriangle(reader));
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
