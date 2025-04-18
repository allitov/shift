package ru.cft.miner.model.record;

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

public class RecordsManager {

    private final String fileName = "task3/src/main/resources/records.json";
    private final ObjectMapper objectMapper;
    private List<RecordData> records;

    public RecordsManager() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        readData();
    }

    public boolean checkNewRecord(String gameType, int time) {
        Optional<RecordData> existingRecord = records.stream()
                .filter(record -> record.gameType().equalsIgnoreCase(gameType))
                .findFirst();

        return existingRecord.map(recordData -> time < recordData.timeValue()).orElse(true);
    }

    public void addRecord(String gameType, String winnerName, int time) {
        if (!checkNewRecord(gameType, time)) {
            return;
        }

        records.removeIf(record -> record.gameType().equalsIgnoreCase(gameType));

        records.add(new RecordData(gameType, winnerName, time));

        writeData();
    }

    public void readData() {
        File file = new File(fileName);

        if (!file.exists()) {
            records = new ArrayList<>();
            return;
        }

        try (InputStream inputStream = new FileInputStream(fileName)) {
            TypeReference<List<RecordData>> typeReference = new TypeReference<>() {};
            records = objectMapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            records = new ArrayList<>();
        }
    }

    private void writeData() {
        try (OutputStream outputStream = new FileOutputStream(fileName)) {
            objectMapper.writeValue(outputStream, records);
        } catch (IOException e) {
            System.err.println("Ошибка при записи файла рекордов: " + e.getMessage());
            throw new RuntimeException("Не удалось сохранить рекорды", e);
        }
    }

    public List<RecordData> getAllRecords() {
        return new ArrayList<>(records);
    }
}