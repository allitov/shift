package ru.shift.multiplicationtable.impl;

import ru.shift.multiplicationtable.TableFormatter;

public class TableFormatterImpl implements TableFormatter {

    private static final String COLUMN_SEPARATOR = "|";
    private static final String DIVIDER_COLUMN_SEPARATOR = "+";
    private static final String CELL_FLOOR = "-";

    @Override
    public String getFirstCellFormat(int size) {
        int cellWidth = calculateCellWidth(size);

        return "%" + cellWidth + "s";
    }

    @Override
    public String getCellFormat(int size) {
        int cellWidth = calculateCellWidth(size * size);

        return COLUMN_SEPARATOR + "%" + cellWidth + "s";
    }

    @Override
    public String getDividerFormat(int size) {
        int firstCellWidth = calculateCellWidth(size);
        int cellWidth = calculateCellWidth(size * size);
        int dividerSize = firstCellWidth + (cellWidth + 1) * size;
        StringBuilder rowDivider = new StringBuilder(dividerSize);
        String cellFloor = CELL_FLOOR.repeat(cellWidth);
        rowDivider.append(CELL_FLOOR.repeat(firstCellWidth));
        for (int i = 0; i < size; i++) {
            rowDivider.append(DIVIDER_COLUMN_SEPARATOR).append(cellFloor);
        }

        return rowDivider.toString();
    }

    private int calculateCellWidth(int maxNum) {
        return maxNum > 0 ? (int) Math.log10(maxNum) + 1 : 1;
    }
}
