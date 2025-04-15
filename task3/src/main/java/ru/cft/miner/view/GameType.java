package ru.cft.miner.view;

public enum GameType {
    NOVICE(10, 10, 10),
    MEDIUM(20, 20, 20),
    EXPERT(30, 30, 30);

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
