package ru.cft.miner.model;

import java.util.Random;

public class GameModel {

    private Cell[][] field;
    private int rows;
    private int cols;

    private int totalBombs;
    private int remainingBombs;

    private boolean isGameStarted;
    private boolean isGameOver;
    private boolean isGameWon;

    public void initGame(int rows, int cols, int bombs) {
        this.rows = rows;
        this.cols = cols;
        this.totalBombs = bombs;
        this.remainingBombs = bombs;
        this.isGameStarted = false;
        this.isGameOver = false;
        this.isGameWon = false;
        this.field = new Cell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.field[i][j] = new Cell();
            }
        }
        placeBombs(bombs);
    }

    public void revealCell(int row, int col) {
        Cell cell = this.field[row][col];
        if (!cell.isRevealed()) {
            cell.setRevealed(true);
        }
    }

    public void setFlag(int row, int col) {
        Cell cell = field[row][col];
        cell.setFlagged(!cell.isFlagged());
    }

    private void placeBombs(int bombs) {
        Random rand = new Random();
        int bombsPlaced = 0;
        while (bombsPlaced < bombs) {
            int row = rand.nextInt(rows);
            int col = rand.nextInt(cols);
            if (!field[row][col].isMine()) {
                field[row][col].setMine(true);
                incrementCellsAround(row, col);
                bombsPlaced++;
            }
        }
    }

    private void incrementCellsAround(int row, int col) {
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (isCellValid(i, j)) {
                    field[i][j].incrementMinesAround();
                }
            }
        }
    }

    private boolean isCellValid(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols && !field[row][col].isMine();
    }

    public Cell getCell(int row, int col) {
        return field[row][col];
    }

    public Cell[][] getField() {
        return field;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}
