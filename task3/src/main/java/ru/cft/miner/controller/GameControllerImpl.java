package ru.cft.miner.controller;

import ru.cft.miner.model.GameModel;
import ru.cft.miner.view.GameType;
import ru.cft.miner.view.GameView;

public class GameControllerImpl {

    private final GameView view;
    private final GameModel model;

    private GameType gameType = GameType.NOVICE;

    public GameControllerImpl(GameView view, GameModel model) {
        this.view = view;
        this.model = model;

        view.setNewGameListener(e -> initGame());
        view.setExitGameListener(e -> System.exit(0));
        view.setCellEventListener((x, y, buttonType) -> {
            switch (buttonType) {
                case LEFT_BUTTON -> openCell(y, x);
                case RIGHT_BUTTON -> setFlag(y, x);
                case MIDDLE_BUTTON -> openCellsAround(y, x);
            }
        });
        view.setGameTypeListener(this::changeGameType);
        view.setNameListener(this::setRecord);
        view.setHighScoresMenuAction(e -> {
            view.showHighScores(model.getAllRecords());
        });
    }

    public void openCellsAround(int y, int x) {
        model.openCellsAround(y, x);
    }

    public void initGame() {
        view.drawGameField(gameType.getRowsCount(), gameType.getColsCount(), gameType.getMinesCount());
        model.initGame(gameType.getRowsCount(), gameType.getColsCount(), gameType.getMinesCount(), gameType.name());
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

    public void setRecord(String name) {
        model.saveRecord(gameType.name(), name);
    }
}
