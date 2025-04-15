package ru.cft.miner.model;

import ru.cft.miner.model.observer.CellOpeningListener;
import ru.cft.miner.model.observer.FlagChangeListener;
import ru.cft.miner.view.GameType;

public interface GameModel {

    void initGame(GameType gameType);

    void changeFlag(int row, int col);

    void openCell(int row, int col);

    void registerObserver(GameStatusListener observer);

    void removeObserver(GameStatusListener observer);

    void registerObserver(CellOpeningListener observer);

    void removeObserver(CellOpeningListener observer);

    void registerObserver(FlagChangeListener observer);

    void removeObserver(FlagChangeListener observer);
}
