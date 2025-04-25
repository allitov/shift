package ru.cft.miner.view;

import ru.cft.miner.model.GameModel;
import ru.cft.miner.model.GameStatus;
import ru.cft.miner.model.field.CellDto;
import ru.cft.miner.model.field.FlagDto;
import ru.cft.miner.model.listener.CellOpeningListener;
import ru.cft.miner.model.listener.FlagChangeListener;
import ru.cft.miner.model.listener.GameStatusListener;
import ru.cft.miner.model.listener.RecordListener;
import ru.cft.miner.model.listener.TimerListener;
import ru.cft.miner.gameutils.record.RecordData;
import ru.cft.miner.view.listener.CellEventListener;
import ru.cft.miner.view.listener.GameTypeListener;
import ru.cft.miner.view.listener.RecordNameListener;
import ru.cft.miner.view.window.HighScoresWindow;
import ru.cft.miner.view.window.LoseWindow;
import ru.cft.miner.view.window.MainWindow;
import ru.cft.miner.view.window.RecordsWindow;
import ru.cft.miner.view.window.SettingsWindow;
import ru.cft.miner.view.window.WinWindow;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

/**
 * Класс, отвечающий за пользовательский интерфейс игры
 */
public class GameViewImpl implements GameView, CellOpeningListener, GameStatusListener, FlagChangeListener, TimerListener, RecordListener {

    private final MainWindow mainWindow;
    private final WinWindow winWindow;
    private final LoseWindow loseWindow;
    private final SettingsWindow settingsWindow;
    private final HighScoresWindow highScoresWindow;
    private final RecordsWindow recordsWindow;

    /**
     * Создает представление игры и связывает его с моделью
     * 
     * @param model игровая модель
     */
    public GameViewImpl(GameModel model) {
        mainWindow = new MainWindow();
        winWindow = new WinWindow(mainWindow);
        loseWindow = new LoseWindow(mainWindow);
        settingsWindow = new SettingsWindow(mainWindow);
        highScoresWindow = new HighScoresWindow(mainWindow);
        recordsWindow = new RecordsWindow(mainWindow);

        registerAsModelObserver(model);

        mainWindow.setSettingsMenuAction(e -> settingsWindow.setVisible(true));
    }

    /**
     * Устанавливает действие для пункта меню "Рекорды"
     * 
     * @param actionListener обработчик события
     */
    @Override
    public void setHighScoresMenuAction(ActionListener actionListener) {
        mainWindow.setHighScoresMenuAction(actionListener);
    }

    /**
     * Рисует игровое поле с заданными параметрами
     * 
     * @param rows количество строк
     * @param cols количество столбцов
     * @param minesCount количество мин
     */
    @Override
    public void drawGameField(int rows, int cols, int minesCount) {
        mainWindow.createGameField(rows, cols);
        mainWindow.setBombsCount(minesCount);
        mainWindow.setTimerValue(0);
    }

    /**
     * Отображает главное окно игры
     */
    @Override
    public void show() {
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);
    }

    /**
     * Отображает окно с рекордами
     *
     * @param allRecords список всех рекордов
     */
    @Override
    public void showHighScores(List<RecordData> allRecords) {
        updateHighScoreWindow(allRecords, GameType.NOVICE,
                highScoresWindow::setNoviceRecord);
        updateHighScoreWindow(allRecords, GameType.MEDIUM,
                highScoresWindow::setMediumRecord);
        updateHighScoreWindow(allRecords, GameType.EXPERT,
                highScoresWindow::setExpertRecord);

        highScoresWindow.setVisible(true);
    }

    /**
     * Устанавливает слушателя ввода имени для рекорда
     *
     * @param nameListener слушатель ввода имени
     */
    @Override
    public void setNameListener(RecordNameListener nameListener) {
        recordsWindow.setNameListener(nameListener);
    }

    /**
     * Устанавливает слушателя события новой игры
     *
     * @param newGameListener слушатель события новой игры
     */
    @Override
    public void setNewGameListener(ActionListener newGameListener) {
        mainWindow.setNewGameMenuAction(newGameListener);
        winWindow.setNewGameListener(newGameListener);
        loseWindow.setNewGameListener(newGameListener);
    }

    /**
     * Устанавливает слушателя события выхода из игры
     *
     * @param exitGameListener слушатель события выхода
     */
    @Override
    public void setExitGameListener(ActionListener exitGameListener) {
        mainWindow.setExitMenuAction(exitGameListener);
        winWindow.setExitListener(exitGameListener);
        loseWindow.setExitListener(exitGameListener);
    }

    /**
     * Устанавливает слушателя события на ячейках игрового поля
     *
     * @param cellEventListener слушатель события на ячейке
     */
    @Override
    public void setCellEventListener(CellEventListener cellEventListener) {
        mainWindow.setCellListener(cellEventListener);
    }

    /**
     * Устанавливает слушателя изменения типа игры
     *
     * @param gameTypeListener слушатель изменения типа игры
     */
    @Override
    public void setGameTypeListener(GameTypeListener gameTypeListener) {
        settingsWindow.setGameTypeListener(gameTypeListener);
    }

    /**
     * Обрабатывает событие открытия ячеек
     *
     * @param cells список открытых ячеек
     */
    @Override
    public void onCellOpening(List<CellDto> cells) {
        cells.forEach(this::updateCellView);
    }

    /**
     * Обрабатывает событие изменения флага
     * 
     * @param flag данные о флаге
     */
    @Override
    public void onFlagChange(FlagDto flag) {
        GameImage image = flag.isFlagged() ? GameImage.MARKED : GameImage.CLOSED;
        mainWindow.setCellImage(flag.col(), flag.row(), image);
        mainWindow.setBombsCount(flag.flagsRemain());
    }

    /**
     * Обрабатывает изменение статуса игры
     * 
     * @param gameStatus новый статус игры
     */
    @Override
    public void onGameStatusChanged(GameStatus gameStatus) {
        if (gameStatus == GameStatus.WON) {
            winWindow.setVisible(true);
        } else {
            loseWindow.setVisible(true);
        }
    }

    /**
     * Обрабатывает изменение времени игры
     * 
     * @param time текущее время в секундах
     */
    @Override
    public void onTimeChanged(int time) {
        mainWindow.setTimerValue(time);
    }

    /**
     * Обрабатывает событие установления нового рекорда
     */
    @Override
    public void onRecord() {
        recordsWindow.setVisible(true);
    }

    /**
     * Обновляет отображение ячейки в зависимости от её состояния
     *
     * @param cell данные ячейки
     */
    private void updateCellView(CellDto cell) {
        if (cell.isMine()) {
            mainWindow.setCellImage(cell.col(), cell.row(), GameImage.BOMB);
        } else if (cell.minesAround() == 0) {
            mainWindow.setCellImage(cell.col(), cell.row(), GameImage.EMPTY);
        } else {
            mainWindow.setCellImage(cell.col(), cell.row(),
                    GameImage.getNumberImage(cell.minesAround()));
        }
    }

    /**
     * Регистрирует представление как слушателя различных событий модели
     *
     * @param model игровая модель
     */
    private void registerAsModelObserver(GameModel model) {
        model.registerObserver((GameStatusListener) this);
        model.registerObserver((FlagChangeListener) this);
        model.registerObserver((TimerListener) this);
        model.registerObserver((CellOpeningListener) this);
        model.registerObserver((RecordListener) this);
    }

    /**
     * Обновляет информацию о рекорде в окне рекордов
     * 
     * @param records список рекордов
     * @param gameType тип игры
     * @param recordSetter метод для установки рекорда
     */
    private void updateHighScoreWindow(List<RecordData> records, GameType gameType, 
                                      RecordSetter recordSetter) {
        Optional<RecordData> data = records.stream()
                .filter(record -> record.gameType().equalsIgnoreCase(gameType.name()))
                .findFirst();
                
        if (data.isPresent()) {
            recordSetter.setRecord(data.get().winnerName(), data.get().timeValue());
        } else {
            recordSetter.setRecord("N/A", 0);
        }
    }

    /**
     * Функциональный интерфейс для установки рекорда в окне
     */
    @FunctionalInterface
    private interface RecordSetter {
        void setRecord(String name, int time);
    }
}