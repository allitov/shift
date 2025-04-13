package ru.cft.miner.model;

import java.util.List;

public interface CellObserver {

    void onCellUpdate(List<Cell> cells);
}
