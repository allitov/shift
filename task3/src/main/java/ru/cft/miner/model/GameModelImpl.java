package ru.cft.miner.model;

import ru.cft.miner.model.observer.CellOpeningListener;
import ru.cft.miner.model.observer.FlagChangeListener;
import ru.cft.miner.view.GameType;

import java.util.List;

public class GameModelImpl implements GameModel {

    private int cellsLeft;
    private int minesCount;
    private GameStatus status;

    private GameField gameField;
    private FlagChanger flagChanger;
    private final MinesGenerator minesGenerator = new MinesGenerator();
    private final CellOpener cellOpener = new CellOpener();

    private CellOpeningListener cellOpeningListener;
    private GameStatusListener gameStatusListener;
    private FlagChangeListener flagChangeListener;

    @Override
    public void initGame(GameType gameType) {
        int rows = gameType.getRowsCount();
        int cols = gameType.getColsCount();
        minesCount = gameType.getMinesCount();

        gameField = new GameField(rows, cols);
        flagChanger = new FlagChanger(minesCount);
        cellsLeft = rows * cols - minesCount;

        status = GameStatus.INITIALIZED;
    }

    @Override
    public void changeFlag(int row, int col) {
        if (status != GameStatus.INITIALIZED && status != GameStatus.STARTED) {
            return;
        }

        boolean isFlagChanged = flagChanger.changeFlag(gameField, row, col);
        if (isFlagChanged && cellOpeningListener != null) {
            flagChangeListener.onFlagChange(new FlagDto(row, col, gameField.isCellFlagged(row, col), flagChanger.getFlagsLeft()));
        }
    }

    @Override
    public void openCell(int row, int col) {
        if (status == GameStatus.INITIALIZED) {
            minesGenerator.generateMines(gameField, minesCount, row, col);
            status = GameStatus.STARTED;
        }

        if (status != GameStatus.STARTED) {
            return;
        }

        List<CellDto> openedCells = cellOpener.openCells(gameField, row, col);
        if (!openedCells.isEmpty() && cellOpeningListener != null) {
            cellOpeningListener.onCellOpening(openedCells);
        }
    }

    @Override
    public void registerObserver(GameStatusListener observer) {
        gameStatusListener = observer;
    }

    @Override
    public void removeObserver(GameStatusListener observer) {
        gameStatusListener = null;
    }

    @Override
    public void registerObserver(CellOpeningListener observer) {
        cellOpeningListener = observer;
    }

    @Override
    public void removeObserver(CellOpeningListener observer) {
        cellOpeningListener = null;
    }

    @Override
    public void registerObserver(FlagChangeListener observer) {
        flagChangeListener = observer;
    }

    @Override
    public void removeObserver(FlagChangeListener observer) {
        flagChangeListener = null;
    }
}
