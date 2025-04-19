package ru.cft.miner.model;

import ru.cft.miner.model.field.CellDto;
import ru.cft.miner.model.field.CellOpener;
import ru.cft.miner.model.field.FlagDto;
import ru.cft.miner.model.field.FlagManager;
import ru.cft.miner.model.field.GameField;
import ru.cft.miner.model.field.MinesGenerator;
import ru.cft.miner.model.listener.CellOpeningListener;
import ru.cft.miner.model.listener.FlagChangeListener;
import ru.cft.miner.model.listener.GameStatusListener;
import ru.cft.miner.model.listener.RecordListener;
import ru.cft.miner.model.listener.TimerListener;
import ru.cft.miner.model.record.RecordData;
import ru.cft.miner.model.record.RecordsManager;
import ru.cft.miner.model.timer.Timer;

import java.util.List;

/**
 * Основная реализация игровой модели
 */
public class GameModelImpl implements GameModel {

    private int cellsToOpen;
    private int minesCount;
    private GameStatus status;
    private String gameType;

    private GameField gameField;
    private FlagManager flagManager;
    private final MinesGenerator minesGenerator;
    private final CellOpener cellOpener;
    private final Timer timer;
    private final RecordsManager recordsManager;

    private CellOpeningListener cellOpeningListener;
    private GameStatusListener gameStatusListener;
    private FlagChangeListener flagChangeListener;
    private RecordListener recordListener;

    public GameModelImpl() {
        minesGenerator = new MinesGenerator();
        cellOpener = new CellOpener();
        timer = new Timer();
        recordsManager = new RecordsManager();
    }

    @Override
    public void initGame(int rows, int cols, int minesCount, String gameType) {
        this.gameType = gameType;
        this.minesCount = minesCount;

        gameField = new GameField(rows, cols);
        flagManager = new FlagManager(minesCount);
        cellsToOpen = rows * cols - minesCount;

        timer.stop();
        timer.reset();

        status = GameStatus.INITIALIZED;
    }

    @Override
    public void changeFlag(int row, int col) {
        if (!isGameActive()) {
            return;
        }

        boolean isFlagChanged = flagManager.changeFlag(gameField, row, col);
        if (isFlagChanged && flagChangeListener != null) {
            FlagDto flagDto = new FlagDto(
                row, 
                col, 
                gameField.isCellFlagged(row, col), 
                flagManager.getFlagsLeft()
            );
            flagChangeListener.onFlagChange(flagDto);
        }
    }

    @Override
    public void openCell(int row, int col) {
        if (status == GameStatus.INITIALIZED) {
            startGame(row, col);
        }

        if (status != GameStatus.STARTED) {
            return;
        }

        List<CellDto> openedCells = cellOpener.openCell(gameField, row, col);
        if (!openedCells.isEmpty() && cellOpeningListener != null) {
            flagManager.returnOpenedFlags(openedCells, gameField);
            cellOpeningListener.onCellOpening(openedCells);
            checkGameStatus(openedCells);
        }
    }

    @Override
    public void openCellsAround(int row, int col) {
        if (status != GameStatus.STARTED) {
            return;
        }

        List<CellDto> openedCells = cellOpener.openCellsAround(gameField, row, col);
        if (!openedCells.isEmpty() && cellOpeningListener != null) {
            flagManager.returnOpenedFlags(openedCells, gameField);
            cellOpeningListener.onCellOpening(openedCells);
            checkGameStatus(openedCells);
        }
    }

    @Override
    public void saveRecord(String gameType, String name) {
        recordsManager.addRecord(gameType, name, timer.getTime());
    }

    @Override
    public List<RecordData> getAllRecords() {
        return recordsManager.getAllRecords();
    }

    /**
     * Проверяет, активна ли игра (инициализирована или запущена)
     */
    private boolean isGameActive() {
        return status == GameStatus.INITIALIZED || status == GameStatus.STARTED;
    }

    /**
     * Начинает игру
     */
    private void startGame(int firstClickRow, int firstClickCol) {
        minesGenerator.generateMines(gameField, minesCount, firstClickRow, firstClickCol);
        status = GameStatus.STARTED;
        timer.start();
    }

    /**
     * Проверяет статус игры после открытия ячеек
     */
    private void checkGameStatus(List<CellDto> openedCells) {
        if (openedCells.stream().anyMatch(CellDto::isMine)) {
            endGameWithLoss();
            return;
        }

        cellsToOpen -= openedCells.size();

        if (cellsToOpen <= 0) {
            endGameWithWin();
        }
    }

    /**
     * Заканчивает игру с проигрышем
     */
    private void endGameWithLoss() {
        status = GameStatus.LOST;
        timer.stop();
        
        if (gameStatusListener != null) {
            gameStatusListener.onGameStatusChanged(GameStatus.LOST);
        }
    }

    /**
     * Заканчивает игру с победой
     */
    private void endGameWithWin() {
        status = GameStatus.WON;
        timer.stop();
        
        boolean isRecord = recordsManager.checkNewRecord(gameType, timer.getTime());
        if (isRecord && recordListener != null) {
            recordListener.onRecord();
        }
        
        if (gameStatusListener != null) {
            gameStatusListener.onGameStatusChanged(GameStatus.WON);
        }
    }

    // Методы регистрации слушателей

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
        timer.setListener(null);
    }

    @Override
    public void registerObserver(RecordListener observer) {
        recordListener = observer;
    }

    @Override
    public void removeObserver(RecordListener observer) {
        recordListener = null;
    }
}