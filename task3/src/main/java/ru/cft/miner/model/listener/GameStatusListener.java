package ru.cft.miner.model.listener;

import ru.cft.miner.model.GameStatus;

public interface GameStatusListener {

    void onGameStatusChanged(GameStatus gameStatus);
}
