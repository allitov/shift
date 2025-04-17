package ru.cft.miner.view;

import ru.cft.miner.model.GameModel;
import ru.cft.miner.model.GameStatus;
import ru.cft.miner.model.field.CellDto;
import ru.cft.miner.model.field.FlagDto;
import ru.cft.miner.model.observer.CellOpeningListener;
import ru.cft.miner.model.observer.FlagChangeListener;
import ru.cft.miner.model.observer.GameStatusListener;
import ru.cft.miner.model.observer.TimerListener;
import ru.cft.miner.view.listener.CellEventListener;
import ru.cft.miner.view.listener.GameTypeListener;
import ru.cft.miner.view.window.HighScoresWindow;
import ru.cft.miner.view.window.LoseWindow;
import ru.cft.miner.view.window.MainWindow;
import ru.cft.miner.view.window.RecordsWindow;
import ru.cft.miner.view.window.SettingsWindow;
import ru.cft.miner.view.window.WinWindow;

import java.awt.event.ActionListener;
import java.util.List;

public class GameView implements CellOpeningListener, GameStatusListener, FlagChangeListener, TimerListener {

    private final MainWindow mainWindow = new MainWindow();
    private final WinWindow winWindow = new WinWindow(mainWindow);
    private final LoseWindow loseWindow = new LoseWindow(mainWindow);
    private final SettingsWindow settingsWindow = new SettingsWindow(mainWindow);
    private final HighScoresWindow highScoresWindow = new HighScoresWindow(mainWindow);
    private final RecordsWindow recordsWindow = new RecordsWindow(mainWindow);

    private GameModel model;

    public GameView(GameModel model) {
        this.model = model;
        model.registerObserver((GameStatusListener) this);
        model.registerObserver((FlagChangeListener) this);
        model.registerObserver((TimerListener) this);
        model.registerObserver((CellOpeningListener) this);
        mainWindow.setHighScoresMenuAction(e -> highScoresWindow.setVisible(true));
        mainWindow.setSettingsMenuAction(e -> settingsWindow.setVisible(true));
    }

    public void drawGameField(int rows, int cols, int minesCount) {
        mainWindow.createGameField(rows, cols);
        mainWindow.setBombsCount(minesCount);
        mainWindow.setTimerValue(0);
    }

    public void show() {
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);
    }

    @Override
    public void onCellOpening(List<CellDto> cells) {
        for (var cell : cells) {
            if (cell.isMine()) {
                mainWindow.setCellImage(cell.col(), cell.row(), GameImage.BOMB);
            } else if (cell.minesAround() == 0) {
                mainWindow.setCellImage(cell.col(), cell.row(), GameImage.EMPTY);
            } else {
                mainWindow.setCellImage(cell.col(), cell.row(), GameImage.getNumberImage(cell.minesAround()));
            }
        }
    }

    @Override
    public void onFlagChange(FlagDto flag) {
        if (flag.isFlagged()) {
            mainWindow.setCellImage(flag.col(), flag.row(), GameImage.MARKED);
        } else {
            mainWindow.setCellImage(flag.col(), flag.row(), GameImage.CLOSED);
        }
        mainWindow.setBombsCount(flag.flagsRemain());
    }

    @Override
    public void onGameStatusChanged(GameStatus gameStatus) {
        if (gameStatus == GameStatus.WON) {
            winWindow.setVisible(true);
        } else {
            loseWindow.setVisible(true);
        }
    }

    @Override
    public void onTimeChanged(int time) {
        mainWindow.setTimerValue(time);
    }

    public void setNewGameListener(ActionListener newGameListener) {
        mainWindow.setNewGameMenuAction(newGameListener);
        winWindow.setNewGameListener(newGameListener);
        loseWindow.setNewGameListener(newGameListener);
    }

    public void setExitGameListener(ActionListener exitGameListener) {
        mainWindow.setExitMenuAction(exitGameListener);
        winWindow.setExitListener(exitGameListener);
        loseWindow.setExitListener(exitGameListener);
    }

    public void setCellEventListener(CellEventListener cellEventListener) {
        mainWindow.setCellListener(cellEventListener);
    }

    public void setGameTypeListener(GameTypeListener gameTypeListener) {
        settingsWindow.setGameTypeListener(gameTypeListener);
    }
}
