package ru.cft.miner.model.observer;

import ru.cft.miner.model.field.CellDto;

import java.util.List;

public interface CellOpeningListener {

    void onCellOpening(List<CellDto> cells);
}
