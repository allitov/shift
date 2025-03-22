package ru.shift.figurecharacteristics.input;

import ru.shift.figurecharacteristics.figure.entity.FigureType;

import java.util.Arrays;
import java.util.Objects;

public class FigureData {

    private FigureType type;
    private String[] params;

    public FigureData(FigureType type, String[] params) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(params);
        this.type = type;
        this.params = params;
    }

    public void setType(FigureType type) {
        Objects.requireNonNull(type);
        this.type = type;
    }

    public FigureType getType() {
        return type;
    }

    public void setParams(String[] params) {
        Objects.requireNonNull(params);
        this.params = params;
    }

    public String[] getParams() {
        return Arrays.copyOf(params, params.length);
    }
}
