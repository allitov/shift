package ru.cft.miner.model;

import java.util.List;

public class GameModelImpl implements GameModel {

    private int cellsLeft;
    private GameStatus status;

    private GameField gameField;
    private FlagChanger flagChanger;
    private final MinesGenerator minesGenerator = new MinesGenerator();
    private final CellOpener cellOpener = new CellOpener();

    private CellStatusListener cellStatusListener;
    private GameStatusListener gameStatusListener;

    @Override
    public void initGame(GameType gameType) {
        int rows = gameType.getRowsCount();
        int cols = gameType.getColsCount();
        int minesCount = gameType.getMinesCount();

        gameField = new GameField(rows, cols);
        flagChanger = new FlagChanger(minesCount);
        cellsLeft = rows * cols - minesCount;

        minesGenerator.generateMines(gameField, minesCount, 0, 0);

        status = GameStatus.INITIALIZED;
    }

    @Override
    public void changeFlag(int row, int col) {
        if (status != GameStatus.INITIALIZED && status != GameStatus.STARTED) {
            return;
        }

        boolean isFlagChanged = flagChanger.changeFlag(gameField, row, col);
        if (isFlagChanged && cellStatusListener != null) {
            cellStatusListener.onCellStatusChanged(List.of(gameField.getCellData(row, col)));
        }
    }

    @Override
    public void openCell(int row, int col) {
        if (status != GameStatus.STARTED) {
            return;
        }

        List<CellDto> openedCells = cellOpener.openCells(gameField, row, col);
        if (!openedCells.isEmpty() && cellStatusListener != null) {
            cellStatusListener.onCellStatusChanged(openedCells);
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
    public void registerObserver(CellStatusListener observer) {
        cellStatusListener = observer;
    }

    @Override
    public void removeObserver(CellStatusListener observer) {
        cellStatusListener = null;
    }
}
