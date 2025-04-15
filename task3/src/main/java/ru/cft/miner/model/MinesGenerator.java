package ru.cft.miner.model;

import java.util.Random;

public class MinesGenerator {

    private final Random random = new Random();

    public void generateMines(GameField field, int minesCount, int startRow, int startCol) {
        int rows = field.getRows();
        int cols = field.getCols();
        while (minesCount > 0) {
            int row = random.nextInt(rows);
            int col = random.nextInt(cols);
            boolean isSafeSquare = (row >= startRow - 1 && row <= startRow + 1) &&
                    (col >= startCol - 1 && col <= startCol + 1);
            boolean areCoordsValid = row < rows && col < cols;
            if (!isSafeSquare && areCoordsValid && !field.isCellMine(row, col)) {
                field.setCellMine(row, col, true);
                field.changeMineCounters(row, col);
                minesCount--;
            }
        }
    }
}
