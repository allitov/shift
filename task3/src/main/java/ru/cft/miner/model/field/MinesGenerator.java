package ru.cft.miner.model.field;

import java.util.Random;

/**
 * Класс для генерации мин на игровом поле
 */
public final class MinesGenerator {

    private static final Random RANDOM = new Random();

    private MinesGenerator() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Генерирует мины на поле, исключая область вокруг начальной точки
     * 
     * @param field игровое поле
     * @param minesCount количество мин для генерации
     * @param startRow строка первого клика
     * @param startCol столбец первого клика
     */
    public static void generateMines(GameField field, int minesCount, int startRow, int startCol) {
        int rows = field.getRows();
        int cols = field.getCols();
        int remainingMines = minesCount;
        
        while (remainingMines > 0) {
            int row = RANDOM.nextInt(rows);
            int col = RANDOM.nextInt(cols);

            if (isSafeZone(row, col, startRow, startCol)) {
                continue;
            }

            if (field.areCoordsValid(row, col) && !field.isCellMine(row, col)) {
                field.setCellMine(row, col, true);
                updateMineCounters(field, row, col);
                remainingMines--;
            }
        }
    }

    /**
     * Проверяет, находится ли позиция в безопасной зоне вокруг первого клика
     */
    private static boolean isSafeZone(int row, int col, int startRow, int startCol) {
        return (row >= startRow - 1 && row <= startRow + 1) &&
               (col >= startCol - 1 && col <= startCol + 1);
    }

    /**
     * Обновляет счетчики мин вокруг указанной позиции
     */
    private static void updateMineCounters(GameField field, int row, int col) {
        field.changeMineCounters(row, col);
    }
}