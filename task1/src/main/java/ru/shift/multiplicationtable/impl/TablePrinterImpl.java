package ru.shift.multiplicationtable.impl;

import ru.shift.multiplicationtable.TablePrinter;

import java.io.PrintWriter;

public class TablePrinterImpl implements TablePrinter {

    private final PrintWriter writer;

    public TablePrinterImpl(PrintWriter writer) {
        this.writer = writer;
    }

    @Override
    public void printFormattedTable(String[][] table, String firstCellFormat, String cellFormat, String dividerFormat) {
        for (String[] row : table) {
            writer.print(String.format(firstCellFormat, row[0]));
            for (int col = 1; col < row.length; col++) {
                writer.print(String.format(cellFormat, row[col]));
            }
            writer.println();
            writer.println(dividerFormat);
        }
        writer.flush();
    }
}
