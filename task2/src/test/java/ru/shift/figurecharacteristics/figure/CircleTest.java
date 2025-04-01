package ru.shift.figurecharacteristics.figure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CircleTest {

    @DisplayName("Test circle name initialization")
    @Test
    void testCircleNameInitialization() {
        Circle circle = new Circle(5);

        assertEquals("Круг", circle.getName());
    }

    @DisplayName("Test circle radius calculation")
    @ParameterizedTest(name = "[{index}] - circle with radius {0}")
    @ValueSource(doubles = {5, 10})
    void testCircleRadiusCalculation(double radius) {
        Circle circle = new Circle(radius);

        assertEquals(radius, circle.getRadius());
    }

    @DisplayName("Test circle diameter calculation")
    @ParameterizedTest(name = "[{index}] - circle with radius {0}")
    @ValueSource(doubles = {5, 10})
    void testCircleDiameterCalculation(double radius) {
        double diameter = calculateDiameter(radius);

        Circle circle = new Circle(radius);

        assertEquals(diameter, circle.getDiameter());
    }

    @DisplayName("Test circle area calculation")
    @ParameterizedTest(name = "[{index}] - circle with radius {0}")
    @ValueSource(doubles = {5, 10})
    void testCircleAreaCalculation(double radius) {
        double area = calculateArea(radius);

        Circle circle = new Circle(radius);

        assertEquals(area, circle.getArea());
    }

    @DisplayName("Test circle perimeter calculation")
    @ParameterizedTest(name = "[{index}] - circle with radius {0}")
    @ValueSource(doubles = {5, 10})
    void testCirclePerimeterCalculation(double radius) {
        double perimeter = calculatePerimeter(radius);

        Circle circle = new Circle(radius);

        assertEquals(perimeter, circle.getPerimeter());
    }

    @DisplayName("Test circle radius validation")
    @ParameterizedTest(name = "[{index}] - validate radius {0}")
    @ValueSource(doubles = {-1, 0, -1e-10})
    void testCircleRadiusValidation(double radius) {
        assertThrows(IllegalArgumentException.class, () -> new Circle(radius));
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
