package ru.cft.miner.controller;

import ru.cft.miner.view.GameType;

public interface GameController {

    void openCellsAround(int row, int col);

    void initGame();

    void changeGameType(GameType gameType);

    void setFlag(int row, int col);

    void openCell(int row, int col);

    void setRecord(String name);
}
