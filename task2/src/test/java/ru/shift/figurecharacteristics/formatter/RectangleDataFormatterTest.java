package ru.shift.figurecharacteristics.formatter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shift.figurecharacteristics.figure.Rectangle;

import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RectangleDataFormatterTest {

    @Mock
    private Rectangle rectangle;

    @DisplayName("Test rectangle data formatting")
    @Test
    void testRectangleDataFormatting() {
        when(rectangle.getName()).thenReturn("Прямоугольник");
        when(rectangle.getArea()).thenReturn(15.2);
        when(rectangle.getPerimeter()).thenReturn(7.25);
        when(rectangle.getDiagonal()).thenReturn(5.);
        when(rectangle.getLength()).thenReturn(10.);
        when(rectangle.getWidth()).thenReturn(15.);
        String expected = createExpectedString();
        var formatter = new RectangleDataFormatter(new DecimalFormat("#.##"), "мм");

        String actual = formatter.format(rectangle);

        assertEquals(expected, actual);
    }

    private String createExpectedString() {
        String lineSeparator = System.lineSeparator();
        return "Тип фигуры: Прямоугольник" + lineSeparator +
                "Площадь: 15,2 кв. мм" + lineSeparator +
                "Периметр: 7,25 мм" + lineSeparator +
                "Диагональ: 5 мм" + lineSeparator +
                "Длина: 10 мм" + lineSeparator +
                "Ширина: 15 мм";
    }
}
