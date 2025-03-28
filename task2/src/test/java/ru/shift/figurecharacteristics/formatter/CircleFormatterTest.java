package ru.shift.figurecharacteristics.formatter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shift.figurecharacteristics.figure.Circle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CircleFormatterTest {

    @Mock
    private Circle circle;

    @DisplayName("Test circle data formatting")
    @Test
    void testCircleDataFormatting() {
        when(circle.getName()).thenReturn("Круг");
        when(circle.getArea()).thenReturn(15.2);
        when(circle.getPerimeter()).thenReturn(7.25);
        when(circle.getRadius()).thenReturn(5.);
        when(circle.getDiameter()).thenReturn(10.);
        String expected = createExpectedString();
        var formatter = new CircleFormatter();

        String actual = formatter.format(circle);

        assertEquals(expected, actual);
    }

    private String createExpectedString() {
        String lineSeparator = System.lineSeparator();
        return "Тип фигуры: Круг" + lineSeparator +
                "Площадь: 15,2 кв. мм" + lineSeparator +
                "Периметр: 7,25 мм" + lineSeparator +
                "Радиус: 5 мм" + lineSeparator +
                "Диаметр: 10 мм";
    }
}
