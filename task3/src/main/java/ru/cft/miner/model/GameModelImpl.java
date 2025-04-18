package ru.cft.miner.model;

import ru.cft.miner.model.field.CellDto;
import ru.cft.miner.model.field.CellOpener;
import ru.cft.miner.model.field.FlagChanger;
import ru.cft.miner.model.field.FlagDto;
import ru.cft.miner.model.field.GameField;
import ru.cft.miner.model.field.MinesGenerator;
import ru.cft.miner.model.observer.CellOpeningListener;
import ru.cft.miner.model.observer.FlagChangeListener;
import ru.cft.miner.model.observer.GameStatusListener;
import ru.cft.miner.model.observer.RecordListener;
import ru.cft.miner.model.observer.TimerListener;
import ru.cft.miner.model.record.RecordData;
import ru.cft.miner.model.record.RecordsManager;
import ru.cft.miner.model.timer.Timer;

import java.util.List;

public class GameModelImpl implements GameModel {

    private int cellsLeft;
    private int minesCount;
    private GameStatus status;

    private GameField gameField;
    private FlagChanger flagChanger;
    private final MinesGenerator minesGenerator = new MinesGenerator();
    private final CellOpener cellOpener = new CellOpener();
    private final Timer timer = new Timer();
    private final RecordsManager recordsManager = new RecordsManager();

    private CellOpeningListener cellOpeningListener;
    private GameStatusListener gameStatusListener;
    private FlagChangeListener flagChangeListener;
    private RecordListener recordListener;

    @Override
    public void initGame(int rows, int cols, int minesCount) {
        this.minesCount = minesCount;

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
            timer.start();
        }

        if (status != GameStatus.STARTED) {
            return;
        }

        List<CellDto> openedCells = cellOpener.openCells(gameField, row, col);
        if (!openedCells.isEmpty() && cellOpeningListener != null) {
            cellOpeningListener.onCellOpening(openedCells);
            checkGameStatus(openedCells);
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

    @Override
    public void registerObserver(TimerListener observer) {
        timer.setListener(observer);
    }

    @Override
    public void removeObserver(TimerListener observer) {

    }

    @Override
    public void registerObserver(RecordListener observer) {
        recordListener = observer;
    }

    @Override
    public void removeObserver(RecordListener observer) {
        recordListener = null;
    }

    @Override
    public void saveRecord(String gameType, String name) {
        recordsManager.addRecord(gameType, name, timer.getTime());
    }

    private void checkGameStatus(List<CellDto> openedCells) {
        if (openedCells.size() == 1 && openedCells.get(0).isMine()) {
            status = GameStatus.LOST;
            timer.stop();
            if (gameStatusListener != null) {
                gameStatusListener.onGameStatusChanged(GameStatus.LOST);
            }
        } else {
            cellsLeft -= openedCells.size();
            if (cellsLeft == 0) {
                status = GameStatus.WON;
                timer.stop();
                boolean isRecord = recordsManager.checkNewRecord("novice", timer.getTime());
                if (isRecord && recordListener != null) {
                    recordListener.onRecord();

                }
                if (gameStatusListener != null) {
                    gameStatusListener.onGameStatusChanged(GameStatus.WON);
                }
            }
        }
    }

    public List<RecordData> getAllRecords() {
        return recordsManager.getAllRecords();
    }

    public void setRecordListener(RecordListener recordListener) {
        this.recordListener = recordListener;
    }
}
