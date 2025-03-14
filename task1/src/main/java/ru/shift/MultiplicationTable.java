package ru.shift;

public class MultiplicationTable {

    public void printTable(int size) {
        int cellWidth = calculateCellWidth(size * size);
        int firstCellWidth = calculateCellWidth(size);

        String cellFormat = "%" + cellWidth + "s|";
        String firstCellFormat = "%" + firstCellWidth + "s|";
        String rowDivider = buildRowDivider(firstCellWidth, cellWidth, size);

        System.out.printf(firstCellFormat, "");
        for (int i = 1; i <= size; i++) {
            System.out.printf(cellFormat, i);
        }
        System.out.println();
        System.out.println(rowDivider);

        for (int i = 1; i <= size; i++) {
            System.out.printf(firstCellFormat, i);
            for (int j = 1; j <= size; j++) {
                System.out.printf(cellFormat, i * j);
            }
            System.out.println();
            System.out.println(rowDivider);
        }
    }

    private int calculateCellWidth(int maxNum) {
        int cellWidth = 0;
        while (maxNum != 0) {
            maxNum = maxNum / 10;
            cellWidth++;
        }

        return cellWidth;
    }

    private String buildRowDivider(int firstCellWidth, int cellWidth, int size) {
        StringBuilder rowDivider = new StringBuilder();
        rowDivider.append("-".repeat(firstCellWidth)).append("+");
        String cellFloor = "-".repeat(cellWidth);
        for (int i = 0; i < size; i++) {
            rowDivider.append(cellFloor).append("+");
        }

        return rowDivider.toString();
    }
}
