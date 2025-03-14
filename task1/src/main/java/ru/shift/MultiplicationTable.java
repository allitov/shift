package ru.shift;

public class MultiplicationTable {

    public void printTable(int size) {
        int firstCellWidth = calculateCellWidth(size);
        int cellWidth = calculateCellWidth(size * size);

        String firstCellFormat = "%" + firstCellWidth + "s";
        String cellFormat = "|%" + cellWidth + "s";
        String rowDivider = buildRowDivider(firstCellWidth, cellWidth, size);

        System.out.printf(firstCellFormat, "");
        for (int col = 1; col <= size; col++) {
            System.out.printf(cellFormat, col);
        }
        System.out.println("\n" + rowDivider);

        for (int row = 1; row <= size; row++) {
            System.out.printf(firstCellFormat, row);
            for (int col = 1; col <= size; col++) {
                System.out.printf(cellFormat, row * col);
            }
            System.out.println("\n" + rowDivider);
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
        rowDivider.append("-".repeat(firstCellWidth));
        String cellFloor = "-".repeat(cellWidth);
        for (int i = 0; i < size; i++) {
            rowDivider.append("+").append(cellFloor);
        }

        return rowDivider.toString();
    }
}
