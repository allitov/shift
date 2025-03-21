package ru.shift.figurecharacteristics.input;

import ru.shift.figurecharacteristics.figure.FigureType;

import java.util.Arrays;
import java.util.Objects;

public class FigureData {

    private FigureType type;
    private double[] params;

    public FigureData(FigureType type, double[] params) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(params);
        this.type = type;
        this.params = params;
    }

    @Override
    public String toString() {
        return "Type: " + type + ", Params: " + Arrays.toString(params);
    }

    public void setType(FigureType type) {
        Objects.requireNonNull(type);
        this.type = type;
    }

    public FigureType getType() {
        return type;
    }

    public void setParams(double[] params) {
        Objects.requireNonNull(params);
        this.params = params;
    }

    public double[] getParams() {
        return Arrays.copyOf(params, params.length);
    }
}
