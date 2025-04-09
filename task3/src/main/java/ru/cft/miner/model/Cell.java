package ru.cft.miner.model;

public class Cell {

    private boolean isRevealed;
    private boolean isMine;
    private boolean isFlagged;
    private int minesAround;

    @Override
    public String toString() {
        if (isMine) {
            return "X";
        }

        return String.valueOf(minesAround);
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public int getMinesAround() {
        return minesAround;
    }

    public void incrementMinesAround() {
        this.minesAround++;
    }
}
