package ru.cft.miner.controller;

import ru.cft.miner.model.GameModel;
import ru.cft.miner.model.observer.CellOpeningListener;
import ru.cft.miner.model.observer.FlagChangeListener;
import ru.cft.miner.model.observer.GameStatusListener;
import ru.cft.miner.view.GameType;
import ru.cft.miner.view.MainWindow;

public class GameControllerImpl {

    private final MainWindow view;
    private final GameModel model;

    private GameType gameType;

    public GameControllerImpl(MainWindow view, GameModel model, GameType gameType) {
        this.view = view;
        this.model = model;
        this.gameType = gameType;
        model.registerObserver((CellOpeningListener) view);
        model.registerObserver((FlagChangeListener) view);
        model.registerObserver((GameStatusListener) view);
    }

    public void initGame() {
        view.createGameField(gameType.getRowsCount(), gameType.getColsCount());
        model.initGame(gameType.getRowsCount(), gameType.getColsCount(), gameType.getMinesCount());
    }

    public void changeGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public void setFlag(int row, int col) {
        model.changeFlag(row, col);
    }

    public void openCell(int row, int col) {
        model.openCell(row, col);
    }
}
