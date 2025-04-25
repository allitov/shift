package ru.cft.miner.controller;

import ru.cft.miner.gameutils.record.RecordsManager;
import ru.cft.miner.model.GameModel;
import ru.cft.miner.view.ButtonType;
import ru.cft.miner.view.GameType;
import ru.cft.miner.view.GameView;

/**
 * Контроллер игры, реализующий взаимодействие между представлением и моделью.
 * Отвечает за обработку пользовательских действий и передачу их в модель.
 */
public class GameControllerImpl implements GameController {

    private final GameView view;
    private final GameModel model;
    private final RecordsManager recordsManager;

    private GameType gameType;

    /**
     * Создает контроллер и инициализирует связи между представлением и моделью.
     *
     * @param view представление игры
     * @param model модель игры
     * @param recordsManager менеджер рекордов
     */
    public GameControllerImpl(GameView view, GameModel model, RecordsManager recordsManager) {
        this.recordsManager = recordsManager;
        this.view = view;
        this.model = model;
        this.gameType = GameType.NOVICE;

        initializeEventListeners();
    }

    /**
     * Открывает ячейки вокруг указанной координаты.
     *
     * @param row строка
     * @param col столбец
     */
    public void openCellsAround(int row, int col) {
        model.openCellsAround(row, col);
    }

    /**
     * Инициализирует новую игру с текущими параметрами.
     */
    public void initGame() {
        view.drawGameField(
                gameType.getRowsCount(),
                gameType.getColsCount(),
                gameType.getMinesCount()
        );
        
        model.initGame(
                gameType.getRowsCount(),
                gameType.getColsCount(),
                gameType.getMinesCount(),
                gameType.name()
        );
    }

    /**
     * Изменяет тип игры и начинает новую игру.
     *
     * @param gameType новый тип игры
     */
    public void changeGameType(GameType gameType) {
        this.gameType = gameType;
        initGame();
    }

    /**
     * Устанавливает или снимает флаг на указанной ячейке.
     *
     * @param row строка
     * @param col столбец
     */
    public void setFlag(int row, int col) {
        model.changeFlag(row, col);
    }

    /**
     * Открывает указанную ячейку.
     *
     * @param row строка
     * @param col столбец
     */
    public void openCell(int row, int col) {
        model.openCell(row, col);
    }

    /**
     * Сохраняет рекорд с указанным именем.
     *
     * @param name имя игрока
     */
    public void setRecord(String name) {
        recordsManager.addRecord(gameType.name(), name);
    }

    /**
     * Регистрирует обработчики событий для представления.
     */
    private void initializeEventListeners() {
        view.setNewGameListener(e -> initGame());

        view.setExitGameListener(e -> System.exit(0));

        view.setCellEventListener(this::handleCellEvent);

        view.setGameTypeListener(this::changeGameType);

        view.setNameListener(this::setRecord);

        view.setHighScoresMenuAction(e -> view.showHighScores(recordsManager.getAllRecords()));
    }

    /**
     * Обрабатывает событие нажатия на ячейку игрового поля.
     *
     * @param x координата ячейки по горизонтали (столбец)
     * @param y координата ячейки по вертикали (строка)
     * @param buttonType тип нажатой кнопки мыши
     */
    private void handleCellEvent(int x, int y, ButtonType buttonType) {
        switch (buttonType) {
            case LEFT_BUTTON -> openCell(y, x);
            case RIGHT_BUTTON -> setFlag(y, x);
            case MIDDLE_BUTTON -> openCellsAround(y, x);
        }
    }
}