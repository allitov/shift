package ru.cft.miner.controller;

import ru.cft.miner.model.GameModel;
import ru.cft.miner.model.GameStatus;
import ru.cft.miner.view.ButtonType;
import ru.cft.miner.view.CellEventListener;
import ru.cft.miner.view.MainWindow;

public class GameController implements CellEventListener {

    private final GameModel model;
    private final MainWindow view;

    public GameController(GameModel model, MainWindow view) {
        this.model = model;
        this.view = view;
        model.setFlagListener(view);
        model.setCellRevealListener(view);
        model.setLoseListener(view);
        view.setCellListener(this);
    }

    public void startNewGame(int row, int col, int bombs) {
        model.initGame(row, col, bombs);
        view.createGameField(row, col);
    }

    @Override
    public void onMouseClick(int x, int y, ButtonType buttonType) {
        switch (buttonType) {
            case LEFT_BUTTON -> {
                if (model.getStatus() == GameStatus.INIT) {
                    model.placeBombs(x, y);
                }
                model.revealCell(x, y);
            }
            case RIGHT_BUTTON -> model.setFlag(x, y);
            case MIDDLE_BUTTON -> {/* TODO: not implemented yet */}
        }
    }
}
