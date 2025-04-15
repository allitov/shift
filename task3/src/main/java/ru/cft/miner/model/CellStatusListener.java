package ru.cft.miner.model;

import java.util.List;

public interface CellStatusListener {

    void onCellStatusChanged(List<CellDto> cells);
}
