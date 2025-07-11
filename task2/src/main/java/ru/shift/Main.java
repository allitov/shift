package ru.shift;

import org.apache.commons.cli.CommandLine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.figurecharacteristics.factory.FigureFactory;
import ru.shift.figurecharacteristics.figure.Figure;
import ru.shift.figurecharacteristics.formatter.FigureFormatter;
import ru.shift.figurecharacteristics.options.AppOption;
import ru.shift.figurecharacteristics.options.AppOptionsContainer;
import ru.shift.figurecharacteristics.printer.ConsoleResultPrinter;
import ru.shift.figurecharacteristics.printer.FileResultPrinter;
import ru.shift.figurecharacteristics.printer.ResultPrinter;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        try {
            AppOptionsContainer appOptionsContainer = new AppOptionsContainer(args);
            CommandLine options = appOptionsContainer.getOptions();

            Figure figure;
            try (BufferedReader br = new BufferedReader(new FileReader(options.getOptionValue(AppOption.DATA_FILE.getOption())))) {
                figure = FigureFactory.create(br);
            }

            String result = FigureFormatter.createFormattedString(figure);
            ResultPrinter printer;
            if (options.hasOption(AppOption.FILE_OUTPUT.getOption())) {
                printer = new FileResultPrinter(options.getOptionValue(AppOption.FILE_OUTPUT.getOption()));
            } else {
                printer = new ConsoleResultPrinter();
            }
            printer.print(result);
        } catch (Exception e) {
            logger.error("Error occurred: ", e);
        }
    }
}