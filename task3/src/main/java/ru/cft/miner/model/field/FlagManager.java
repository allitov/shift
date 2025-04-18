package ru.cft.miner.model.field;

import java.util.List;

/**
 * Класс для управления флагами на игровом поле
 */
public class FlagManager {

    private int flagsLeft;

    /**
     * Создает экземпляр с указанным количеством доступных флагов
     * 
     * @param initialFlagsCount начальное количество флагов
     */
    public FlagManager(int initialFlagsCount) {
        this.flagsLeft = initialFlagsCount;
    }

    /**
     * Меняет состояние флага на указанной ячейке (устанавливает/снимает)
     * 
     * @param field игровое поле
     * @param row строка
     * @param col столбец
     * @return true, если состояние флага изменено, иначе false
     */
    public boolean changeFlag(GameField field, int row, int col) {
        if (!isValidCellForFlagging(field, row, col)) {
            return false;
        }

        return field.isCellFlagged(row, col) 
                ? removeFlag(field, row, col) 
                : placeFlag(field, row, col);
    }

    /**
     * Возвращает флаги, которые были на открытых ячейках
     * 
     * @param openedCells список открытых ячеек
     */
    public void returnOpenedFlags(List<CellDto> openedCells) {
        long flagsCount = openedCells.stream()
                .filter(CellDto::isFlagged)
                .count();
                
        flagsLeft += (int) flagsCount;
    }

    /**
     * Возвращает количество оставшихся флагов
     * 
     * @return количество флагов
     */
    public int getFlagsLeft() {
        return flagsLeft;
    }

    /**
     * Проверяет валидность ячейки для установки/снятия флага
     */
    private boolean isValidCellForFlagging(GameField field, int row, int col) {
        return field.areCoordsValid(row, col) && !field.isCellRevealed(row, col);
    }

    /**
     * Устанавливает флаг на указанную ячейку
     * 
     * @param field игровое поле
     * @param row строка
     * @param col столбец
     * @return true, если флаг установлен, иначе false
     */
    private boolean placeFlag(GameField field, int row, int col) {
        if (flagsLeft <= 0) {
            return false;
        }

        field.setCellFlagged(row, col, true);
        flagsLeft--;

        return true;
    }

    /**
     * Снимает флаг с указанной ячейки
     * 
     * @param field игровое поле
     * @param row строка
     * @param col столбец
     * @return true, если флаг снят
     */
    private boolean removeFlag(GameField field, int row, int col) {
        field.setCellFlagged(row, col, false);
        flagsLeft++;

        return true;
    }
}