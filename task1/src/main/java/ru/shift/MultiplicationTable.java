package ru.shift;

public class MultiplicationTable {

    public void printTable(int size) {
        int firstCellWidth = calculateCellWidth(size);
        int cellWidth = calculateCellWidth(size * size);

        String firstCellFormat = "%" + firstCellWidth + "s";
        String cellFormat = "|%" + cellWidth + "s";
        String rowDivider = buildRowDivider(firstCellWidth, cellWidth, size);

        printHeader(size, firstCellFormat, cellFormat, rowDivider);
        printBody(size, firstCellFormat, cellFormat, rowDivider);
    }

    private void printHeader(int size, String firstCellFormat, String cellFormat, String rowDivider) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format(firstCellFormat, ""));
        for (int col = 1; col <= size; col++) {
            builder.append(String.format(cellFormat, col));
        }
        builder.append("\n").append(rowDivider);
        System.out.println(builder);
    }

    private void printBody(int size, String firstCellFormat, String cellFormat, String rowDivider) {
        StringBuilder builder = new StringBuilder();
        for (int row = 1; row <= size; row++) {
            builder.append(String.format(firstCellFormat, row));
            for (int col = 1; col <= size; col++) {
                builder.append(String.format(cellFormat, row * col));
            }
            builder.append("\n").append(rowDivider);
            System.out.println(builder);
            builder.setLength(0);
        }
    }

    private int calculateCellWidth(int maxNum) {
        return maxNum > 0 ? (int) Math.log10(maxNum) + 1 : 1;
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
