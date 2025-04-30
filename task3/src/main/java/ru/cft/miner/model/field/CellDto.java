package ru.cft.miner.model.field;

public record CellDto(int row, int col, int minesAround, boolean isFlagged, boolean isMine) {

}
