package ru.shift.multiplicationtable;

public interface TableFormatter {

    String getFirstCellFormat(int size);

    String getCellFormat(int size);

    String getDividerFormat(int size);
}
