package ru.shift.multiplicationtable;

import ru.shift.multiplicationtable.impl.TableFormatterImpl;
import ru.shift.multiplicationtable.impl.TableGeneratorImpl;
import ru.shift.multiplicationtable.impl.TablePrinterImpl;

import java.io.PrintWriter;

public class MultiplicationTable {

    private final TableGenerator generator;
    private final TableFormatter formatter;
    private final TablePrinter printer;

    public MultiplicationTable(PrintWriter writer) {
        this.generator = new TableGeneratorImpl();
        this.formatter = new TableFormatterImpl();
        this.printer = new TablePrinterImpl(writer);
    }

    public void print(int size) {
        String[][] table = generator.generateTable(size);
        String firstCellFormat = formatter.getFirstCellFormat(size);
        String cellFormat = formatter.getCellFormat(size);
        String dividerFormat = formatter.getDividerFormat(size);
        printer.printFormattedTable(table, firstCellFormat, cellFormat, dividerFormat);
    }
}
