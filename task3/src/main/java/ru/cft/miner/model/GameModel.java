package ru.cft.miner.model;

public interface GameModel {

    void initGame(int rowsCount, int colsCount, int minesCount);

    void startGame(int startRow, int startCol);

    void changeFlag(int row, int col);

    void openCell(int row, int col);

    GameStatus getStatus();

    void registerObserver(CellObserver observer);

    void removeObserver(CellObserver observer);

    void registerObserver(FlagObserver observer);

    void removeObserver(FlagObserver observer);

    void registerObserver(WinObserver observer);

    void removeObserver(WinObserver observer);

    void registerObserver(LoseObserver observer);

    void removeObserver(LoseObserver observer);
}
