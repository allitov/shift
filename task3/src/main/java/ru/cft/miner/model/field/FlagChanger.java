package ru.cft.miner.model.field;

import java.util.List;

public class FlagChanger {

    private int flagsLeft;

    public FlagChanger(int flagsLeft) {
        this.flagsLeft = flagsLeft;
    }

    public boolean changeFlag(GameField field, int row, int col) {
        if (!field.areCoordsValid(row, col) || field.isCellRevealed(row, col)) {
            return false;
        }

        return field.isCellFlagged(row, col) ? removeFlag(field, row, col) : placeFlag(field, row, col);
    }

    public void returnOpenedFlags(List<CellDto> openedCells) {
        long flags = openedCells.stream()
                .filter(CellDto::isFlagged)
                .count();
        flagsLeft += (int) flags;
    }

    public int getFlagsLeft() {
        return flagsLeft;
    }

    private boolean placeFlag(GameField field, int row, int col) {
        if (flagsLeft == 0) {
            return false;
        }

        field.setCellFlagged(row, col, true);
        flagsLeft--;

        return true;
    }

    private boolean removeFlag(GameField field, int row, int col) {
        field.setCellFlagged(row, col, false);
        flagsLeft++;

        return true;
    }
}
