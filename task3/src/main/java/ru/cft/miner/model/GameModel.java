package ru.cft.miner.model;

import ru.cft.miner.model.observer.CellOpeningListener;
import ru.cft.miner.model.observer.FlagChangeListener;
import ru.cft.miner.model.observer.GameStatusListener;
import ru.cft.miner.model.observer.RecordListener;
import ru.cft.miner.model.observer.TimerListener;
import ru.cft.miner.model.record.RecordData;

import java.util.List;

public interface GameModel {

    void initGame(int rows, int cols, int minesCount, String gameType);

    void changeFlag(int row, int col);

    void openCell(int row, int col);

    void saveRecord(String gameType, String name);

    void registerObserver(GameStatusListener observer);

    void removeObserver(GameStatusListener observer);

    void registerObserver(CellOpeningListener observer);

    void removeObserver(CellOpeningListener observer);

    void registerObserver(FlagChangeListener observer);

    void removeObserver(FlagChangeListener observer);

    void registerObserver(TimerListener observer);

    void removeObserver(TimerListener observer);

    void registerObserver(RecordListener observer);

    void removeObserver(RecordListener observer);

    List<RecordData> getAllRecords();
}
