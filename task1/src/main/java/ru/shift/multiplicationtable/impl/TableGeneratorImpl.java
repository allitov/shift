package ru.shift.multiplicationtable.impl;

import ru.shift.multiplicationtable.TableGenerator;

public class TableGeneratorImpl implements TableGenerator {

    @Override
    public String[][] generateTable(int size) {
        String[][] table = new String[size + 1][size + 1];
        table[0][0] = "";
        for (int col = 1; col <= size; col++) {
            table[0][col] = String.valueOf(col);
        }
        for (int row = 1; row <= size; row++) {
            table[row][0] = String.valueOf(row);
            for (int col = 1; col <= size; col++) {
                table[row][col] = String.valueOf(col * row);
            }
        }

        return table;
    }
}
