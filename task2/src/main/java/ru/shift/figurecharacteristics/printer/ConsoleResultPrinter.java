package ru.shift.figurecharacteristics.printer;

public class ConsoleResultPrinter implements ResultPrinter {

    @Override
    public void print(String text) {
        System.out.println(text);
    }
}
