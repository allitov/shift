package ru.cft.miner.model;

public class Cell {

    private final int row;
    private final int col;

    private boolean isRevealed;
    private boolean isFlagged;
    private boolean isMine;
    private int minesAroundCounter;

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
