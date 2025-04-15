package ru.cft.miner.model.observer;

import ru.cft.miner.model.GameStatus;

public interface GameStatusListener {

    void onGameStatusChanged(GameStatus gameStatus);
}
