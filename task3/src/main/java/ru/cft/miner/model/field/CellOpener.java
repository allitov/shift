package ru.cft.miner.model.field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CellOpener {

    public List<CellDto> openCell(GameField field, int row, int col) {
        if (!field.areCoordsValid(row, col)) {
            return Collections.emptyList();
        }

        if (field.isCellRevealed(row, col) || field.isCellFlagged(row, col)) {
            return Collections.emptyList();
        }

        field.setCellRevealed(row, col, true);
        Queue<CellDto> queue = new LinkedList<>();
        queue.add(field.getCellData(row, col));

        return openField(queue, field);
    }

    public List<CellDto> openCellsAround(GameField field, int row, int col) {
        if (!field.areCoordsValid(row, col)) {
            return Collections.emptyList();
        }

        if (!canOpenAroundCell(field, row, col)) {
            return Collections.emptyList();
        }

        Queue<CellDto> queue = new LinkedList<>();
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                if (field.areCoordsValid(r, c) && !field.isCellRevealed(r, c) && !field.isCellFlagged(r, c)) {
                    field.setCellRevealed(r, c, true);
                    queue.add(field.getCellData(r, c));
                }
            }
        }

        return openField(queue, field);
    }

    private List<CellDto> openField(Queue<CellDto> queue, GameField field) {
        List<CellDto> cellsToOpen = new ArrayList<>();
        while (!queue.isEmpty()) {
            CellDto currentCell = queue.poll();
            cellsToOpen.add(currentCell);
            if (currentCell.minesAround() > 0) {
                continue;
            }
            for (int r = currentCell.row() - 1; r <= currentCell.row() + 1; r++) {
                for (int c = currentCell.col() - 1; c <= currentCell.col() + 1; c++) {
                    if (!field.areCoordsValid(r, c)) {
                        continue;
                    }

                    if (!field.isCellRevealed(r, c)) {
                        field.setCellRevealed(r, c, true);
                        queue.add(field.getCellData(r, c));
                    }
                }
            }
        }

        return cellsToOpen;
    }

    private boolean canOpenAroundCell(GameField field, int row, int col) {
        if (!field.isCellRevealed(row, col) || field.isCellFlagged(row, col) || field.getCellMinesAroundCounter(row, col) == 0) {
            return false;
        }

        int flagsCount = 0;
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                if (field.areCoordsValid(r, c) && field.isCellFlagged(r, c)) {
                    flagsCount++;
                }
            }
        }

        return flagsCount == field.getCellMinesAroundCounter(row, col);
    }
}
