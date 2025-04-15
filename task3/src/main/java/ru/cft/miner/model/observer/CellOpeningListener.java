package ru.cft.miner.model.observer;

import ru.cft.miner.model.CellDto;

import java.util.List;

public interface CellOpeningListener {

    void onCellOpening(List<CellDto> cells);
}
