package ru.cft.miner.view;

public enum GameType {
    NOVICE(9, 9, 1),
    MEDIUM(16, 16, 40),
    EXPERT(16, 30, 99);

    private final int rowsCount;
    private final int colsCount;
    private final int minesCount;

    GameType(int rowsCount, int colsCount, int minesCount) {
        this.rowsCount = rowsCount;
        this.colsCount = colsCount;
        this.minesCount = minesCount;
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public int getColsCount() {
        return colsCount;
    }

    public int getMinesCount() {
        return minesCount;
    }
}
