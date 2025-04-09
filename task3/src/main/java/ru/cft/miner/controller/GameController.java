package ru.cft.miner.controller;

import ru.cft.miner.model.Cell;
import ru.cft.miner.model.GameModel;
import ru.cft.miner.view.ButtonType;
import ru.cft.miner.view.CellEventListener;
import ru.cft.miner.view.GameImage;
import ru.cft.miner.view.MainWindow;

public class GameController implements CellEventListener {

    private final GameModel model;
    private final MainWindow view;

    public GameController(GameModel model, MainWindow view) {
        this.model = model;
        this.view = view;
        view.setCellListener(this);
    }

    public void startNewGame(int row, int col, int bombs) {
        model.initGame(row, col, bombs);
        view.createGameField(row, col);
    }

    private void updateView() {
        for (int row = 0; row < model.getRows(); row++) {
            for (int col = 0; col < model.getCols(); col++) {
                Cell cell = model.getCell(row, col);
                if (cell.isRevealed()) {
                    if (cell.isMine()) {
                        view.setCellImage(row, col, GameImage.BOMB);
                    } else {
                        view.setCellImage(row, col, GameImage.getNumberImage(cell.getMinesAround()));
                    }
                } else if (cell.isFlagged()) {
                    view.setCellImage(row, col, GameImage.MARKED);
                } else {
                    view.setCellImage(row, col, GameImage.CLOSED);
                }
            }
        }
    }

    @Override
    public void onMouseClick(int x, int y, ButtonType buttonType) {
        switch (buttonType) {
            case LEFT_BUTTON -> model.revealCell(x, y);
            case RIGHT_BUTTON -> model.setFlag(x, y);
            case MIDDLE_BUTTON -> {/* TODO: not implemented yet */}
        }
        updateView();
    }
}
