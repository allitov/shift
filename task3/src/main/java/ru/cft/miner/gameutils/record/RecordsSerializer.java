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

/**
 * Класс для сериализации и десериализации данных в JSON формате
 */
public class RecordsSerializer {

    private static final String RECORDS_PATH = "./records.json";
    private final ObjectMapper objectMapper;
    
    public RecordsSerializer() {
        objectMapper = configureObjectMapper();
    }

    /**
     * Загружает список рекордов из файла
     */
    public List<RecordData> loadRecords() {
        File file = new File(RECORDS_PATH);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (InputStream inputStream = new FileInputStream(RECORDS_PATH)) {
            TypeReference<List<RecordData>> typeReference = new TypeReference<>() {};
            return objectMapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Сохраняет список рекордов в файл
     *
     * @param records - список рекордов для записи
     */
    public void saveRecords(List<RecordData> records) {
        try (OutputStream outputStream = new FileOutputStream(RECORDS_PATH)) {
            objectMapper.writeValue(outputStream, records);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось сохранить рекорды", e);
        }
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
}
