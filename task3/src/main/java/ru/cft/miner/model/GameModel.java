package ru.cft.miner.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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

    private FlagListener flagListener;
    private CellRevealListener cellRevealListener;

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
                this.field[i][j] = new Cell(i, j);
            }
        }
        placeBombs(bombs);
    }

    public void revealCell(int row, int col) {
        Cell cell = this.field[row][col];
        if (!cell.isRevealed() && !cell.isFlagged()) {
            List<Cell> cellsToOpen = openEmptyCells(row, col);
            if (cellRevealListener != null) {
                cellRevealListener.onCellRevealed(cellsToOpen);
            }
        }
    }

    public void setFlag(int row, int col) {
        Cell cell = field[row][col];
        if (!cell.isRevealed()) {
            cell.setFlagged(!cell.isFlagged());
            if (flagListener != null) {
                flagListener.onFlagSet(cell);
            }
        }
    }

    private List<Cell> openEmptyCells(int row, int col) {
        Cell cell = field[row][col];
        if (cell.isRevealed() || cell.isFlagged()) {
            return Collections.emptyList();
        }

        List<Cell> cellsToOpen = new LinkedList<>();
        cell.setRevealed(true);
        cellsToOpen.add(cell);
        if (cell.isMine() || cell.getMinesAround() != 0) {
            cellsToOpen.add(cell);
            return cellsToOpen;
        }

        Queue<Cell> openedCells = new LinkedList<>();
        openedCells.add(cell);
        while (!openedCells.isEmpty()) {
            Cell cellToOpen = openedCells.poll();
            for (int i = cellToOpen.getRow() - 1; i <= cellToOpen.getRow() + 1; i++) {
                for (int j = cellToOpen.getCol() - 1; j <= cellToOpen.getCol() + 1; j++) {
                    if (i == cellToOpen.getRow() && j == cellToOpen.getCol()) {
                        continue;
                    }

                    if (!isCellValid(i, j)) {
                        continue;
                    }

                    Cell cellNeighbour = field[i][j];
                    if (!cellNeighbour.isRevealed()) {
                        cellNeighbour.setRevealed(true);
                        cellsToOpen.add(cellNeighbour);
                        if (cellNeighbour.getMinesAround() == 0) {
                            openedCells.add(cellNeighbour);
                        }
                    }
                }
            }
        }

        return cellsToOpen;
//        Queue<Cell> openedCells = new LinkedList<>();
//        openedCells.add(field[row][col]);
//        List<Cell> openedCellsList = new ArrayList<>();
//        while (!openedCells.isEmpty()) {
//            Cell cell = openedCells.poll();
//            if (cell.isRevealed() || cell.isFlagged()) {
//                continue;
//            }
//
//            if (cell.isMine()) {
//                openedCells.add(cell);
//                break;
//            }
//
//            cell.setRevealed(true);
//            for (int i = cell.getRow() - 1; i <= cell.getRow() + 1; i++) {
//                for (int j = cell.getCol() - 1; j <= cell.getCol() + 1; j++) {
//                    if (i == cell.getRow() && j == cell.getCol()) {
//                        continue;
//                    }
//                    if (isCellValid(i, j)) {
//                        openedCellsList.add(field[i][j]);
//                        if (field[i][j].getMinesAround() == 0) {
//                            openedCells.add(field[i][j]);
//                        }
//                    }
//                }
//            }
//        }
//
//        return openedCellsList;
//        Queue<Cell> openedCells = new LinkedList<>();
//        openedCells.add(field[row][col]);
//        List<Cell> cellsToReveal = new LinkedList<>();
//        while (!openedCells.isEmpty()) {
//            Cell cell = openedCells.poll();
//            if (cell.isRevealed()) {
//                continue;
//            }
//            cell.setRevealed(true);
//            for (int i = row - 1; i <= row + 1; i++) {
//                for (int j = col - 1; j <= col + 1; j++) {
//                    if (isCellValid(i, j) && !(i == cell.getRow() && j == cell.getCol())) {
//                        Cell cellToOpen = field[i][j];
//                        if (cell.getMinesAround() == 0) {
//                            openedCells.add(cellToOpen);
//                        }
//                        cellsToReveal.add(cellToOpen);
//                    }
//                }
//            }
//        }
//
//        return cellsToReveal;
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

    public void setFlagListener(FlagListener flagListener) {
        this.flagListener = flagListener;
    }

    public void setCellRevealListener(CellRevealListener cellRevealListener) {
        this.cellRevealListener = cellRevealListener;
    }
}
