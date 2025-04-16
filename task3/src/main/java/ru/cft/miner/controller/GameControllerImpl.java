package ru.cft.miner.controller;

import ru.cft.miner.model.GameModel;
import ru.cft.miner.model.observer.CellOpeningListener;
import ru.cft.miner.model.observer.FlagChangeListener;
import ru.cft.miner.model.observer.GameStatusListener;
import ru.cft.miner.model.observer.TimerListener;
import ru.cft.miner.view.GameType;
import ru.cft.miner.view.GameTypeListener;
import ru.cft.miner.view.MainWindow;

public class GameControllerImpl implements GameTypeListener {

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
        model.registerObserver((TimerListener) view);
    }

    public void initGame() {
        view.createGameField(gameType.getRowsCount(), gameType.getColsCount());
        view.setBombsCount(gameType.getMinesCount());
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

    @Override
    public void onGameTypeChanged(GameType gameType) {
        this.gameType = gameType;
        initGame();
    }
}
