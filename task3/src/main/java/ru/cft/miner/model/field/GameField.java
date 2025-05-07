package ru.cft.miner.model.field;

/**
 * Класс, представляющий игровое поле сапёра
 */
public class GameField {

    private final Cell[][] field;
    private final int rows;
    private final int cols;

    /**
     * Создаёт игровое поле с указанными размерами
     * 
     * @param rows количество строк
     * @param cols количество столбцов
     */
    public GameField(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        field = new Cell[rows][cols];
        initField();
    }

    /**
     * Внутренний класс, представляющий ячейку игрового поля
     */
    private static class Cell {

        private final int row;
        private final int col;

        private boolean isRevealed;
        private boolean isFlagged;
        private boolean isMine;
        private int minesAroundCounter;

        /**
         * Создаёт ячейку с указанными координатами
         * 
         * @param row строка
         * @param col столбец
         */
        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }

        /**
         * @return номер столбца
         */
        public int getCol() {
            return col;
        }

        /**
         * @return номер строки
         */
        public int getRow() {
            return row;
        }

        /**
         * Устанавливает или снимает флаг на ячейке
         * 
         * @param flagged флаг установлен (true) или снят (false)
         */
        public void setFlagged(boolean flagged) {
            this.isFlagged = flagged;
        }

        /**
         * @return true, если на ячейке установлен флаг
         */
        public boolean isFlagged() {
            return isFlagged;
        }

        /**
         * @return true, если ячейка содержит мину
         */
        public boolean isMine() {
            return isMine;
        }

        /**
         * Устанавливает или убирает мину в ячейке
         * 
         * @param mine установить (true) или убрать (false) мину
         */
        public void setMine(boolean mine) {
            this.isMine = mine;
        }

        /**
         * Увеличивает счётчик мин вокруг ячейки
         */
        public void incrementMinesAroundCounter() {
            minesAroundCounter++;
        }

        /**
         * @return количество мин вокруг ячейки
         */
        public int getMinesAroundCounter() {
            return minesAroundCounter;
        }

        /**
         * @return true, если ячейка открыта
         */
        public boolean isRevealed() {
            return isRevealed;
        }

        /**
         * Открывает или закрывает ячейку
         * 
         * @param revealed открыть (true) или закрыть (false) ячейку
         */
        public void setRevealed(boolean revealed) {
            isRevealed = revealed;
        }
    }

    /**
     * Проверяет, содержит ли ячейка мину
     * 
     * @param row строка
     * @param col столбец
     * @return true, если ячейка содержит мину
     */
    public boolean isCellMine(int row, int col) {
        validateCoords(row, col);
        return field[row][col].isMine();
    }

    /**
     * Устанавливает или убирает мину в ячейке
     * 
     * @param row строка
     * @param col столбец
     * @param mine установить (true) или убрать (false) мину
     */
    public void setCellMine(int row, int col, boolean mine) {
        validateCoords(row, col);
        field[row][col].setMine(mine);
    }

    /**
     * Проверяет, открыта ли ячейка
     * 
     * @param row строка
     * @param col столбец
     * @return true, если ячейка открыта, иначе false
     */
    public boolean isCellRevealed(int row, int col) {
        validateCoords(row, col);

        return field[row][col].isRevealed();
    }

    /**
     * Открывает или закрывает ячейку
     * 
     * @param row строка
     * @param col столбец
     * @param revealed открыть (true) или закрыть (false) ячейку
     */
    public void setCellRevealed(int row, int col, boolean revealed) {
        validateCoords(row, col);
        field[row][col].setRevealed(revealed);
    }

    /**
     * Проверяет, установлен ли флаг на ячейке
     * 
     * @param row строка
     * @param col столбец
     * @return true, если на ячейке установлен флаг, иначе false
     */
    public boolean isCellFlagged(int row, int col) {
        validateCoords(row, col);

        return field[row][col].isFlagged();
    }

    /**
     * Устанавливает или снимает флаг на ячейке
     * 
     * @param row строка
     * @param col столбец
     * @param flagged установить (true) или снять (false) флаг
     */
    public void setCellFlagged(int row, int col, boolean flagged) {
        validateCoords(row, col);
        field[row][col].setFlagged(flagged);
    }

    /**
     * Обновляет счётчики мин вокруг указанной ячейки
     * 
     * @param row строка
     * @param col столбец
     */
    public void changeMineCounters(int row, int col) {
        validateCoords(row, col);

        forEachNeighbor(row, col, (r, c) -> {
            if (areCoordsValid(r, c)) {
                field[r][c].incrementMinesAroundCounter();
            }
        });
    }

    /**
     * Возвращает количество мин вокруг ячейки
     * 
     * @param row строка
     * @param col столбец
     * @return количество мин вокруг ячейки
     */
    public int getCellMinesAroundCounter(int row, int col) {
        validateCoords(row, col);
        return field[row][col].getMinesAroundCounter();
    }

    /**
     * Возвращает данные ячейки
     * 
     * @param row строка
     * @param col столбец
     * @return объект с данными ячейки
     */
    public CellDto getCellData(int row, int col) {
        validateCoords(row, col);
        Cell cell = field[row][col];

        return new CellDto(
                cell.getRow(),
                cell.getCol(),
                cell.getMinesAroundCounter(),
                cell.isFlagged(),
                cell.isMine()
        );
    }

    /**
     * @return количество строк поля
     */
    public int getRows() {
        return rows;
    }

    /**
     * @return количество столбцов поля
     */
    public int getCols() {
        return cols;
    }

    /**
     * Проверяет валидность координат
     * 
     * @param row строка
     * @param col столбец
     * @return true, если координаты находятся в пределах поля, иначе false
     */
    public boolean areCoordsValid(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    /**
     * Выполняет действие для каждой соседней ячейки (8 окружающих ячеек)
     * 
     * @param row строка центральной ячейки
     * @param col столбец центральной ячейки
     * @param action действие, которое нужно выполнить для каждой соседней ячейки
     */
    private void forEachNeighbor(int row, int col, CellAction action) {
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                if (r == row && c == col) {
                    continue;
                }
                action.execute(r, c);
            }
        }
    }

    /**
     * Функциональный интерфейс для операций над ячейками
     */
    @FunctionalInterface
    private interface CellAction {
        void execute(int row, int col);
    }

    /**
     * Инициализирует игровое поле, создавая ячейки
     */
    private void initField() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                field[row][col] = new Cell(row, col);
            }
        }
    }

    /**
     * Проверяет валидность координат и бросает исключение, если они некорректны
     * 
     * @param row строка
     * @param col столбец
     * @throws IllegalArgumentException если координаты выходят за пределы поля
     */
    private void validateCoords(int row, int col) {
        if (!areCoordsValid(row, col)) {
            throw new IllegalArgumentException(
                    "Неверные координаты (" + row + ", " + col + 
                    ") для поля размером " + rows + "x" + cols
            );
        }
    }
}