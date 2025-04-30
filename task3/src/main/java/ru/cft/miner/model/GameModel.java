package ru.cft.miner.model;

import ru.cft.miner.model.listener.CellOpeningListener;
import ru.cft.miner.model.listener.FlagChangeListener;
import ru.cft.miner.model.listener.GameStatusListener;
import ru.cft.miner.model.listener.GameSummaryListener;

public interface GameModel {

    void initGame(int rows, int cols, int minesCount, String gameType);

    void changeFlag(int row, int col);

    void openCell(int row, int col);

    void openCellsAround(int row, int col);

    void registerObserver(GameStatusListener observer);

    void registerObserver(CellOpeningListener observer);

    void registerObserver(FlagChangeListener observer);

    void registerObserver(GameSummaryListener observer);
}
