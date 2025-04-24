package ru.cft.miner.model;

import ru.cft.miner.model.listener.CellOpeningListener;
import ru.cft.miner.model.listener.FlagChangeListener;
import ru.cft.miner.model.listener.GameStatusListener;
import ru.cft.miner.model.listener.RecordListener;
import ru.cft.miner.model.listener.TimerListener;
import ru.cft.miner.model.record.RecordData;

import java.util.List;

public interface GameModel {

    void initGame(int rows, int cols, int minesCount, String gameType);

    void changeFlag(int row, int col);

    void openCell(int row, int col);

    void openCellsAround(int row, int col);

    void saveRecord(String gameType, String name);

    void registerObserver(GameStatusListener observer);

    void registerObserver(CellOpeningListener observer);

    void registerObserver(FlagChangeListener observer);

    void registerObserver(TimerListener observer);

    void registerObserver(RecordListener observer);

    List<RecordData> getAllRecords();
}
