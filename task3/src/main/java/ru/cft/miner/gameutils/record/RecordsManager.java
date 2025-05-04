package ru.cft.miner.gameutils.record;

import ru.cft.miner.gameutils.timer.Timer;
import ru.cft.miner.model.GameModel;
import ru.cft.miner.model.listener.GameSummaryListener;
import ru.cft.miner.model.listener.RecordListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс для управления рекордами игры
 */
public class RecordsManager implements GameSummaryListener {

    private final RecordsSerializer recordsSerializer;
    private final List<RecordData> records;
    private final Timer timer;
    private final List<RecordListener> recordListeners = new ArrayList<>();

    public RecordsManager(GameModel model, Timer timer) {
        this.recordsSerializer = new RecordsSerializer();
        this.records = recordsSerializer.loadRecords();
        this.timer = timer;
        registerAsModelObserver(model);
    }

    /**
     * Проверяет, является ли указанное время новым рекордом для данного типа игры
     *
     * @param gameType тип игры
     */
    public void checkNewRecord(String gameType) {
        Optional<RecordData> existingRecord = records.stream()
                .filter(record -> record.gameType().equalsIgnoreCase(gameType))
                .findFirst();

        if (existingRecord.map(recordData -> timer.getTime() < recordData.timeValue()).orElse(true)) {
            notifyRecordListeners();
        }
    }

    /**
     * Добавляет новый рекорд, если время лучше существующего
     *
     * @param gameType тип игры
     * @param winnerName имя победителя
     */
    public void addRecord(String gameType, String winnerName) {
        records.removeIf(record -> record.gameType().equalsIgnoreCase(gameType));

        records.add(new RecordData(gameType, winnerName, timer.getTime()));

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

    @Override
    public void onGameSummary(String gameType) {
        timer.stop();
        checkNewRecord(gameType);
    }

    public void registerObserver(RecordListener observer) {
        recordListeners.add(observer);
    }

    public void notifyRecordListeners() {
        recordListeners.forEach(RecordListener::onRecord);
    }

    /**
     * Регистрирует представление как слушателя различных событий модели
     *
     * @param model игровая модель
     */
    private void registerAsModelObserver(GameModel model) {
        model.registerObserver(this);
    }
}