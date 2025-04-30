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
import ru.cft.miner.model.listener.GameSummaryListener;

import java.util.ArrayList;
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

    private final List<CellOpeningListener> cellOpeningListeners = new ArrayList<>();
    private final List<GameStatusListener> gameStatusListeners = new ArrayList<>();
    private final List<FlagChangeListener> flagChangeListeners = new ArrayList<>();
    private final List<GameSummaryListener> gameSummaryListeners = new ArrayList<>();

    /**
     * Инициализирует игру с указанными параметрами
     *
     * @param rows количество строк игрового поля
     * @param cols количество столбцов игрового поля
     * @param minesCount количество мин на поле
     * @param gameType тип игры
     */
    @Override
    public void initGame(int rows, int cols, int minesCount, String gameType) {
        this.gameType = gameType;
        this.minesCount = minesCount;

        gameField = new GameField(rows, cols);
        flagManager = new FlagManager(minesCount);
        cellsToOpen = rows * cols - minesCount;

        status = GameStatus.INITIALIZED;
        notifyGameStatusListeners(GameStatus.INITIALIZED);
    }

    /**
     * Изменяет состояние флага на указанной ячейке
     * 
     * @param row номер строки ячейки
     * @param col номер столбца ячейки
     */
    @Override
    public void changeFlag(int row, int col) {
        if (!isGameActive()) {
            return;
        }

        boolean isFlagChanged = flagManager.changeFlag(gameField, row, col);
        if (isFlagChanged) {
            FlagDto flagDto = new FlagDto(
                row, 
                col, 
                gameField.isCellFlagged(row, col), 
                flagManager.getFlagsLeft()
            );
            notifyFlagChangeListeners(flagDto);
        }
    }

    /**
     * Открывает указанную ячейку на игровом поле.
     * Если это первый клик, инициирует начало игры
     * 
     * @param row номер строки ячейки
     * @param col номер столбца ячейки
     */
    @Override
    public void openCell(int row, int col) {
        if (status == GameStatus.INITIALIZED) {
            startGame(row, col);
        }

        if (status != GameStatus.STARTED) {
            return;
        }

        processOpenedCells(CellOpener.openCell(gameField, row, col));
    }

    /**
     * Открывает все ячейки вокруг указанной, если вокруг неё находится количество флагов,
     * равное числу на ячейке
     * 
     * @param row номер строки ячейки
     * @param col номер столбца ячейки
     */
    @Override
    public void openCellsAround(int row, int col) {
        if (status != GameStatus.STARTED) {
            return;
        }

        processOpenedCells(CellOpener.openCellsAround(gameField, row, col));
    }

    /**
     * Обрабатывает список открытых ячеек: снимает флаги, уведомляет слушателей
     * и проверяет статус игры
     *
     * @param openedCells список открытых ячеек
     */
    private void processOpenedCells(List<CellDto> openedCells) {
        if (openedCells.isEmpty()) {
            return;
        }

        flagManager.returnOpenedFlags(openedCells, gameField);
        notifyCellOpeningListeners(openedCells);
        checkGameStatus(openedCells);
    }

    /**
     * Проверяет, активна ли игра (инициализирована или запущена)
     * 
     * @return true, если игра активна, иначе false
     */
    private boolean isGameActive() {
        return status == GameStatus.INITIALIZED || status == GameStatus.STARTED;
    }

    /**
     * Начинает игру с первым кликом на указанную ячейку
     * 
     * @param firstClickRow строка первого клика
     * @param firstClickCol столбец первого клика
     */
    private void startGame(int firstClickRow, int firstClickCol) {
        MinesGenerator.generateMines(gameField, minesCount, firstClickRow, firstClickCol);
        status = GameStatus.STARTED;
        notifyGameStatusListeners(GameStatus.STARTED);
    }

    /**
     * Проверяет статус игры после открытия ячеек
     * 
     * @param openedCells список открытых ячеек
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
     * и уведомляет слушателей об изменении статуса
     */
    private void endGameWithLoss() {
        status = GameStatus.LOST;
        notifyGameStatusListeners(GameStatus.LOST);
    }

    /**
     * Заканчивает игру с победой
     * и уведомляет слушателей об изменении статуса
     */
    private void endGameWithWin() {
        status = GameStatus.WON;
        notifyGameSummaryListeners(gameType);
        notifyGameStatusListeners(GameStatus.WON);
    }

    /**
     * Уведомляет всех слушателей об открытии ячеек
     * 
     * @param openedCells список открытых ячеек
     */
    private void notifyCellOpeningListeners(List<CellDto> openedCells) {
        cellOpeningListeners.forEach(listener -> listener.onCellOpening(openedCells));
    }
    
    /**
     * Уведомляет всех слушателей об изменении статуса игры
     * 
     * @param status новый статус игры
     */
    private void notifyGameStatusListeners(GameStatus status) {
        gameStatusListeners.forEach(listener -> listener.onGameStatusChanged(status));
    }
    
    /**
     * Уведомляет всех слушателей об изменении флага
     * 
     * @param flagDto данные об изменении флага
     */
    private void notifyFlagChangeListeners(FlagDto flagDto) {
        flagChangeListeners.forEach(listener -> listener.onFlagChange(flagDto));
    }

    private void notifyGameSummaryListeners(String gameType) {
        gameSummaryListeners.forEach(listener -> listener.onGameSummary(gameType));
    }

    /**
     * Регистрирует нового слушателя статуса игры
     * 
     * @param observer слушатель статуса игры
     */
    @Override
    public void registerObserver(GameStatusListener observer) {
        gameStatusListeners.add(observer);
    }

    /**
     * Регистрирует нового слушателя открытия ячеек
     * 
     * @param observer слушатель открытия ячеек
     */
    @Override
    public void registerObserver(CellOpeningListener observer) {
        cellOpeningListeners.add(observer);
    }

    /**
     * Регистрирует нового слушателя изменения флагов
     * 
     * @param observer слушатель изменения флагов
     */
    @Override
    public void registerObserver(FlagChangeListener observer) {
        flagChangeListeners.add(observer);
    }

    /**
     * Регистрирует нового слушателя подведения итогов игры
     *
     * @param observer слушатель подведения итогов
     */
    @Override
    public void registerObserver(GameSummaryListener observer) {
        gameSummaryListeners.add(observer);
    }
}