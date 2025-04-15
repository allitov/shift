package ru.cft.miner.model;

public class GameField {

    private final Cell[][] field;
    private final int rows;
    private final int cols;

    public GameField(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        field = new Cell[rows][cols];
        initField();
    }

    private static class Cell {

        final int row;
        final int col;

        boolean isRevealed;
        boolean isFlagged;
        boolean isMine;
        int minesAroundCounter;

        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getCol() {
            return col;
        }

        public int getRow() {
            return row;
        }

        public void setFlagged(boolean flagged) {
            this.isFlagged = flagged;
        }

        public boolean isFlagged() {
            return isFlagged;
        }

        public boolean isMine() {
            return isMine;
        }

        public void setMine(boolean mine) {
            this.isMine = mine;
        }

        public void incrementMinesAroundCounter() {
            minesAroundCounter++;
        }

        public int getMinesAroundCounter() {
            return minesAroundCounter;
        }

        public boolean isRevealed() {
            return isRevealed;
        }

        public void setRevealed(boolean revealed) {
            isRevealed = revealed;
        }
    }

    public boolean isCellMine(int row, int col) {
        validateCoords(row, col);

        return field[row][col].isMine();
    }

    public void setCellMine(int row, int col, boolean mine) {
        validateCoords(row, col);

        field[row][col].setMine(mine);
    }

    public boolean isCellRevealed(int row, int col) {
        validateCoords(row, col);

        return field[row][col].isRevealed();
    }

    public void setCellRevealed(int row, int col, boolean revealed) {
        validateCoords(row, col);

        field[row][col].setRevealed(revealed);
    }

    public boolean isCellFlagged(int row, int col) {
        validateCoords(row, col);

        return field[row][col].isFlagged();
    }

    public void setCellFlagged(int row, int col, boolean flagged) {
        validateCoords(row, col);

        field[row][col].setFlagged(flagged);
    }

    public void changeMineCounters(int row, int col) {
        validateCoords(row, col);

        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                if (!(r == row && c == col)) {
                    field[r][c].incrementMinesAroundCounter();
                }
            }
        }
    }

    public int getCellMinesAroundCounter(int row, int col) {
        validateCoords(row, col);

        return field[row][col].getMinesAroundCounter();
    }

    public CellDto getCellData(int row, int col) {
        validateCoords(row, col);

        Cell cell = field[row][col];

        return new CellDto(cell.getRow(), cell.getCol(), cell.getMinesAroundCounter(), cell.isFlagged(), cell.isMine());
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public boolean areCoordsValid(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    private void initField() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                field[row][col] = new Cell(row, col);
            }
        }
    }

    private void validateCoords(int row, int col) {
        if (!areCoordsValid(row, col)) {
            throw new IllegalArgumentException("Invalid coordinate (" + row + ", " + col + ") for rows = " + rows + ", cols = " + cols);
        }
    }
}
