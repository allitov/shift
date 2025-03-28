package ru.shift.figurecharacteristics.factory;

import ru.shift.figurecharacteristics.figure.Figure;
import ru.shift.figurecharacteristics.figure.FigureType;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public interface FigureFactory<T extends Figure> {

    Map<FigureType, FigureFactory<?>> registry = ServiceLoader.load(FigureFactory.class).stream()
            .map(ServiceLoader.Provider::get)
            .collect(Collectors.toMap(FigureFactory::getType, f -> (FigureFactory<?>) f));

    FigureType getType();

    T read(BufferedReader reader) throws IOException;

    static Figure create(BufferedReader reader) throws IOException {
        FigureType type = FigureType.valueOf(reader.readLine().toUpperCase());
        FigureFactory<?> factory = registry.get(type);

        return factory.read(reader);
    }
}
