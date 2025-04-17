package ru.cft.miner.model.field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CellOpener {

    public List<CellDto> openCells(GameField field, int row, int col) {
        if (!field.areCoordsValid(row, col)) {
            return Collections.emptyList();
        }

        if (field.isCellRevealed(row, col) || field.isCellFlagged(row, col)) {
            return Collections.emptyList();
        }

        field.setCellRevealed(row, col, true);
        if (field.isCellMine(row, col)) {
            return Collections.singletonList(field.getCellData(row, col));
        }

        List<CellDto> cellsToOpen = new ArrayList<>();
        Queue<CellDto> queue = new LinkedList<>();
        queue.add(field.getCellData(row, col));
        while (!queue.isEmpty()) {
            CellDto currentCell = queue.poll();
            cellsToOpen.add(currentCell);
            if (field.getCellMinesAroundCounter(currentCell.row(), currentCell.col()) > 0) {
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
}
