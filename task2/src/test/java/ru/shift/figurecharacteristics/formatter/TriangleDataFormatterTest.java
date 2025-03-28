package ru.shift.figurecharacteristics.formatter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shift.figurecharacteristics.figure.Triangle;

import java.text.DecimalFormat;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TriangleDataFormatterTest {

    @Mock
    private Triangle triangle;

    @DisplayName("Test triangle data formatting")
    @Test
    void testTriangleDataFormatting() {
        when(triangle.getName()).thenReturn("Треугольник");
        when(triangle.getArea()).thenReturn(15.2);
        when(triangle.getPerimeter()).thenReturn(7.25);
        when(triangle.getA()).thenReturn(1.);
        when(triangle.getB()).thenReturn(3.);
        when(triangle.getC()).thenReturn(5.);
        when(triangle.getSideToAngleMap()).thenReturn(Map.of(1., 2., 3., 4., 5., 6.));
        String expected = createExpectedString();
        var formatter = new TriangleDataFormatter(new DecimalFormat("#.##"), "мм");

        String actual = formatter.format(triangle);

        assertEquals(expected, actual);
    }

    private String createExpectedString() {
        String lineSeparator = System.lineSeparator();
        return "Тип фигуры: Треугольник" + lineSeparator +
                "Площадь: 15,2 кв. мм" + lineSeparator +
                "Периметр: 7,25 мм" + lineSeparator +
                "Сторона a: 1 мм; угол: 2 градусов" + lineSeparator +
                "Сторона b: 3 мм; угол: 4 градусов" + lineSeparator +
                "Сторона c: 5 мм; угол: 6 градусов";
    }
}
