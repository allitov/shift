package ru.cft.miner.controller;

import ru.cft.miner.model.GameModel;
import ru.cft.miner.view.GameType;
import ru.cft.miner.view.GameView;

public class GameControllerImpl {

    private final GameView view;
    private final GameModel model;

    private GameType gameType;

    public GameControllerImpl(GameView view, GameModel model, GameType gameType) {
        this.view = view;
        this.model = model;
        this.gameType = gameType;

        view.setNewGameListener(e -> initGame());
        view.setExitGameListener(e -> System.exit(0));
        view.setCellEventListener((x, y, buttonType) -> {
            switch (buttonType) {
                case LEFT_BUTTON -> openCell(y, x);
                case RIGHT_BUTTON -> setFlag(y, x);
                case MIDDLE_BUTTON -> {/* TODO: not implemented yet */}
            }
        });
        view.setGameTypeListener(this::changeGameType);
    }

    public void initGame() {
        view.drawGameField(gameType.getRowsCount(), gameType.getColsCount(), gameType.getMinesCount());
        model.initGame(gameType.getRowsCount(), gameType.getColsCount(), gameType.getMinesCount());
    }

    public void changeGameType(GameType gameType) {
        this.gameType = gameType;
        initGame();
    }

    public void setFlag(int row, int col) {
        model.changeFlag(row, col);
    }

    public void openCell(int row, int col) {
        model.openCell(row, col);
    }
}
