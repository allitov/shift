package ru.cft.miner.model.field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Класс, отвечающий за открытие ячеек на игровом поле
 */
public final class CellOpener {

    private CellOpener() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Открывает ячейку по заданным координатам
     * 
     * @param field игровое поле
     * @param row строка
     * @param col столбец
     * @return список открытых ячеек
     */
    public static List<CellDto> openCell(GameField field, int row, int col) {
        if (!isValidCellForOpening(field, row, col)) {
            return Collections.emptyList();
        }

        field.setCellRevealed(row, col, true);
        Queue<CellDto> queue = new LinkedList<>();
        queue.add(field.getCellData(row, col));

        return openField(queue, field);
    }

    /**
     * Открывает ячейки вокруг заданной координаты при выполнении условий
     * 
     * @param field игровое поле
     * @param row строка
     * @param col столбец
     * @return список открытых ячеек
     */
    public static List<CellDto> openCellsAround(GameField field, int row, int col) {
        if (!field.areCoordsValid(row, col) || !canOpenAroundCell(field, row, col)) {
            return Collections.emptyList();
        }

        Queue<CellDto> queue = new LinkedList<>();
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                if (isNeighborCellOpenable(field, r, c)) {
                    field.setCellRevealed(r, c, true);
                    queue.add(field.getCellData(r, c));
                }
            }
        }

        return openField(queue, field);
    }

    /**
     * Проверяет, можно ли открыть ячейку
     */
    private static boolean isValidCellForOpening(GameField field, int row, int col) {
        if (!field.areCoordsValid(row, col)) {
            return false;
        }
        return !field.isCellRevealed(row, col) && !field.isCellFlagged(row, col);
    }

    /**
     * Проверяет, можно ли открыть соседнюю ячейку
     */
    private static boolean isNeighborCellOpenable(GameField field, int row, int col) {
        return field.areCoordsValid(row, col) && 
               !field.isCellRevealed(row, col) && 
               !field.isCellFlagged(row, col);
    }

    /**
     * Основной алгоритм открытия ячеек на поле с использованием BFS
     * 
     * @param queue очередь ячеек для обработки
     * @param field игровое поле
     * @return список открытых ячеек
     */
    private static List<CellDto> openField(Queue<CellDto> queue, GameField field) {
        List<CellDto> cellsToOpen = new ArrayList<>();
        
        while (!queue.isEmpty()) {
            CellDto currentCell = queue.poll();
            cellsToOpen.add(currentCell);
            
            // Если вокруг ячейки есть мины, не раскрываем соседние
            if (currentCell.minesAround() > 0) {
                continue;
            }
            
            processNeighbors(currentCell, field, queue);
        }

        return cellsToOpen;
    }

    /**
     * Обрабатывает соседние ячейки текущей ячейки
     */
    private static void processNeighbors(CellDto currentCell, GameField field, Queue<CellDto> queue) {
        for (int r = currentCell.row() - 1; r <= currentCell.row() + 1; r++) {
            for (int c = currentCell.col() - 1; c <= currentCell.col() + 1; c++) {
                if (!field.areCoordsValid(r, c) || field.isCellRevealed(r, c)) {
                    continue;
                }

                field.setCellRevealed(r, c, true);
                queue.add(field.getCellData(r, c));
            }
        }
    }

    /**
     * Проверяет, можно ли открыть ячейки вокруг заданной ячейки
     * (если количество установленных флагов равно количеству мин вокруг)
     */
    private static boolean canOpenAroundCell(GameField field, int row, int col) {
        if (!field.isCellRevealed(row, col) || 
            field.isCellFlagged(row, col) || 
            field.getCellMinesAroundCounter(row, col) == 0) {
            return false;
        }

        return countFlagsAround(field, row, col) == field.getCellMinesAroundCounter(row, col);
    }

    /**
     * Подсчитывает количество флагов вокруг ячейки
     */
    private static int countFlagsAround(GameField field, int row, int col) {
        int flagsCount = 0;
        
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                if (field.areCoordsValid(r, c) && field.isCellFlagged(r, c)) {
                    flagsCount++;
                }
            }
        }
        
        return flagsCount;
    }
}