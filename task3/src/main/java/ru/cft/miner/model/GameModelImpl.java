package ru.cft.miner.model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class GameModelImpl implements GameModel {

    private Cell[][] field;
    private int rows;
    private int cols;

    private int totalMines;
    private int flagsLeft;

    private GameStatus status;

    private CellObserver cellObserver;
    private FlagObserver flagObserver;

    @Override
    public void initGame(int rowsCount, int colsCount, int minesCount) {
        field = new Cell[rowsCount][colsCount];
        this.rows = rowsCount;
        this.cols = colsCount;
        this.totalMines = minesCount;
        this.flagsLeft = minesCount;
        initField();
        status = GameStatus.INITIALIZED;
    }

    @Override
    public void startGame(int startRow, int startCol) {
        if (!areCoordsValid(startRow, startCol)) {
            return;
        }

        placeMines(startRow, startCol);
        status = GameStatus.STARTED;
        openCell(startRow, startCol);
    }

    @Override
    public void changeFlag(int row, int col) {
        if (status != GameStatus.INITIALIZED && status != GameStatus.STARTED) {
            return;
        }

        if (!areCoordsValid(row, col)) {
            return;
        }

        Cell cell = field[row][col];
        if (cell.isRevealed()) {
            return;
        }

        if (cell.isFlagged()) {
            cell.setFlagged(false);
            flagsLeft++;
        } else if (flagsLeft > 0) {
            cell.setFlagged(true);
            flagsLeft--;
        }
    }

    @Override
    public void openCell(int row, int col) {
        if (status != GameStatus.STARTED) {
            return;
        }

        if (!areCoordsValid(row, col)) {
            return;
        }

        Cell cell = field[row][col];
        if (cell.isRevealed() || cell.isFlagged()) {
            return;
        }

        cell.setRevealed(true);
        if (cell.isMine()) {
            status = GameStatus.LOST;
            return;
        }

        Queue<Cell> queue = new LinkedList<>();
        queue.add(cell);
        while (!queue.isEmpty()) {
            Cell currentCell = queue.poll();
            if (currentCell.getMinesAroundCounter() > 0) {
                continue;
            }
            for (int r = currentCell.getRow() - 1; r <= currentCell.getRow() + 1; r++) {
                for (int c = currentCell.getCol() - 1; c <= currentCell.getCol() + 1; c++) {
                    if (!areCoordsValid(r, c)) {
                        continue;
                    }

                    Cell neighbor = field[r][c];
                    if (!neighbor.isRevealed()) {
                        neighbor.setRevealed(true);
                        queue.add(neighbor);
                    }
                }
            }
        }
    }

    @Override
    public GameStatus getStatus() {
        return status;
    }

    @Override
    public void registerObserver(CellObserver observer) {
        cellObserver = observer;
    }

    @Override
    public void removeObserver(CellObserver observer) {
        cellObserver = null;
    }

    @Override
    public void registerObserver(FlagObserver observer) {
        flagObserver = observer;
    }

    @Override
    public void removeObserver(FlagObserver observer) {
        flagObserver = null;
    }

    @Override
    public void registerObserver(WinObserver observer) {

    }

    @Override
    public void removeObserver(WinObserver observer) {

    }

    @Override
    public void registerObserver(LoseObserver observer) {

    }

    @Override
    public void removeObserver(LoseObserver observer) {

    }

    private void initField() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                field[row][col] = new Cell(row, col);
            }
        }
    }

    private void placeMines(int startRow, int startCol) {
        Random rand = new Random();
        int minesLeft = totalMines;
        while (minesLeft > 0) {
            int row = rand.nextInt(rows);
            int col = rand.nextInt(cols);
            boolean isSafeSquare = (row >= startRow - 1 && row <= startRow + 1) &&
                    (col >= startCol - 1 && col <= startCol + 1);
            if (!isSafeSquare && areCoordsValid(row, col) && !field[row][col].isMine()) {
                field[row][col].setMine(true);
                changeMineCounters(row, col);
                minesLeft--;
            }
        }
    }

    private void changeMineCounters(int row, int col) {
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                if (areCoordsValid(r, c) && !(r == row && c == col)) {
                    field[r][c].incrementMinesAroundCounter();
                }
            }
        }
    }

    private boolean areCoordsValid(int row, int col) {
        return (row >= 0 && row < rows) && (col >= 0 && col < cols);
    }
}
