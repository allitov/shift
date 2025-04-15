package ru.cft.miner.controller;

import ru.cft.miner.view.GameType;

public interface GameController {

    void initGame(GameType gameType);

    void openCell(int row, int col);
}
