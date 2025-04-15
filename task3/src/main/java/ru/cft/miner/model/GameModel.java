package ru.cft.miner.model;

public interface GameModel {

    void initGame(GameType gameType);

    void changeFlag(int row, int col);

    void openCell(int row, int col);

    void registerObserver(GameStatusListener observer);

    void removeObserver(GameStatusListener observer);

    void registerObserver(CellStatusListener observer);

    void removeObserver(CellStatusListener observer);
}
