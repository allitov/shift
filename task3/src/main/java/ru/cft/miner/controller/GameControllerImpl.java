package ru.cft.miner.controller;

import ru.cft.miner.model.CellObserver;
import ru.cft.miner.model.FlagObserver;
import ru.cft.miner.model.GameModel;
import ru.cft.miner.model.GameStatus;
import ru.cft.miner.model.LoseObserver;
import ru.cft.miner.model.MineObserver;
import ru.cft.miner.view.ButtonType;
import ru.cft.miner.view.CellEventListener;
import ru.cft.miner.view.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameControllerImpl implements GameController, CellEventListener {

    private final MainWindow view;
    private final GameModel model;

    public GameControllerImpl(MainWindow view, GameModel model) {
        this.view = view;
        this.model = model;
        model.registerObserver((CellObserver) view);
        model.registerObserver((LoseObserver) view);
        model.registerObserver((FlagObserver) view);
        model.registerObserver((MineObserver) view);
        view.setNewGameMenuAction(e -> initializeGame());
        view.setVisible(true);
        view.setCellListener(this);
    }

    public void initializeGame() {
        view.createGameField(10, 10);
        model.initGame(10, 10, 10);
    }

    @Override
    public void onMouseClick(int x, int y, ButtonType buttonType) {
        switch (buttonType) {
            case LEFT_BUTTON -> {
                if (model.getStatus() == GameStatus.INITIALIZED) {
                    model.startGame(y, x);
                } else {
                    model.openCell(y, x);
                }
            }
            case RIGHT_BUTTON -> model.changeFlag(y, x);
            case MIDDLE_BUTTON -> { /* TODO: not implemented yet */ }
        }
    }
}
