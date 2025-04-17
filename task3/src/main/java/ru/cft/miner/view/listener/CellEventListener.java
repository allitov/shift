package ru.cft.miner.view.listener;

import ru.cft.miner.view.ButtonType;

public interface CellEventListener {
    void onMouseClick(int x, int y, ButtonType buttonType);
}
