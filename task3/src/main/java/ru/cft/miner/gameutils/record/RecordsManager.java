package ru.cft.miner.gameutils.record;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс для управления рекордами игры
 */
public class RecordsManager {

    private static final String DEFAULT_RECORDS_PATH = "./records.json";
    
    private final String recordsFilePath;
    private final ObjectMapper objectMapper;
    private final List<RecordData> records;

    /**
     * Создает менеджер рекордов с путем к файлу по умолчанию
     */
    public RecordsManager() {
        this(DEFAULT_RECORDS_PATH);
    }

    /**
     * Создает менеджер рекордов с указанным путем к файлу
     *
     * @param recordsFilePath путь к файлу с рекордами
     */
    public RecordsManager(String recordsFilePath) {
        this.recordsFilePath = recordsFilePath;
        this.objectMapper = configureObjectMapper();
        this.records = loadRecords();
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

        saveRecords();
    }

    /**
     * Возвращает копию списка всех рекордов
     *
     * @return список рекордов
     */
    public List<RecordData> getAllRecords() {
        return new ArrayList<>(records);
    }

    /**
     * Конфигурирует ObjectMapper для работы с JSON
     */
    private ObjectMapper configureObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        return mapper;
    }

    /**
     * Загружает список рекордов из файла
     */
    private List<RecordData> loadRecords() {
        File file = new File(recordsFilePath);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (InputStream inputStream = new FileInputStream(recordsFilePath)) {
            TypeReference<List<RecordData>> typeReference = new TypeReference<>() {};
            return objectMapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Сохраняет список рекордов в файл
     */
    private void saveRecords() {
        try (OutputStream outputStream = new FileOutputStream(recordsFilePath)) {
            objectMapper.writeValue(outputStream, records);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось сохранить рекорды", e);
        }
    }
}