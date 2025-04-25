package ru.cft.miner.gameutils.record;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс для управления рекордами игры
 */
public class RecordsManager {

    private final RecordsSerializer recordsSerializer;
    private final List<RecordData> records;

    public RecordsManager() {
        this.recordsSerializer = new RecordsSerializer();
        this.records = recordsSerializer.loadRecords();
    }

    /**
     * Проверяет, является ли указанное время новым рекордом для данного типа игры
     *
     * @param gameType тип игры
     * @param time время в секундах
     * @return true, если это новый рекорд, иначе false
     */
    public boolean checkNewRecord(String gameType, int time) {
        Optional<RecordData> existingRecord = records.stream()
                .filter(record -> record.gameType().equalsIgnoreCase(gameType))
                .findFirst();

        return existingRecord.map(recordData -> time < recordData.timeValue()).orElse(true);
    }

    /**
     * Добавляет новый рекорд, если время лучше существующего
     *
     * @param gameType тип игры
     * @param winnerName имя победителя
     * @param time время в секундах
     */
    public void addRecord(String gameType, String winnerName, int time) {
        if (!checkNewRecord(gameType, time)) {
            return;
        }

        records.removeIf(record -> record.gameType().equalsIgnoreCase(gameType));

        records.add(new RecordData(gameType, winnerName, time));

        recordsSerializer.saveRecords(records);
    }

    /**
     * Возвращает копию списка всех рекордов
     *
     * @return список рекордов
     */
    public List<RecordData> getAllRecords() {
        return new ArrayList<>(records);
    }
}