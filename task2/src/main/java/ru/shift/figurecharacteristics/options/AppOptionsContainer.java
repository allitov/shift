package ru.shift.figurecharacteristics.options;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class AppOptionsContainer {

    private final CommandLine options;

    public AppOptionsContainer(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption(AppOption.DATA_FILE.getOption());
        options.addOption(AppOption.FILE_OUTPUT.getOption());

        this.options = new DefaultParser().parse(options, args);
    }

    public CommandLine getOptions() {
        return options;
    }
}
