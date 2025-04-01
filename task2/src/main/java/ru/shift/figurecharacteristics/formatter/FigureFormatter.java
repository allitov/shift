package ru.shift.figurecharacteristics.formatter;

import ru.shift.figurecharacteristics.figure.Figure;
import ru.shift.figurecharacteristics.figure.FigureType;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public interface FigureFormatter {

    DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");
    String UNITS = "мм";
    String LINE_SEPARATOR = System.lineSeparator();
    String SPACE = " ";

    Map<FigureType, FigureFormatter> registry = ServiceLoader.load(FigureFormatter.class).stream()
            .map(ServiceLoader.Provider::get)
            .collect(Collectors.toMap(FigureFormatter::getType, f -> f));

    FigureType getType();

    String format(Figure figure);

    static String createFormattedString(Figure figure) {
        FigureFormatter formatter = registry.get(figure.getType());

        return formatter.format(figure);
    }
}
