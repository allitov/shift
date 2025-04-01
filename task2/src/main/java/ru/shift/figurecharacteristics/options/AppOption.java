package ru.shift.figurecharacteristics.options;

import org.apache.commons.cli.Option;

public enum AppOption {

    DATA_FILE("d", "DATA FILE", "File to get data from", true, true),
    FILE_OUTPUT("f", "FILE OUTPUT", "Print result to file", true, false);

    private final Option option;

    AppOption(String shortName, String longName, String description, boolean hasArg, boolean required) {
        this.option = Option.builder(shortName)
                .longOpt(longName)
                .desc(description)
                .hasArg(hasArg)
                .required(required)
                .build();
    }

    public Option getOption() {
        return option;
    }
}
