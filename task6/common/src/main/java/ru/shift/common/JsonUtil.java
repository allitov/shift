package ru.shift.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;

import java.io.IOException;

@UtilityClass
public class JsonUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    public static String toJson(ChatMessage msg) throws IOException {
        return MAPPER.writeValueAsString(msg);
    }

    public static ChatMessage fromJson(String json) throws IOException {
        return MAPPER.readValue(json, ChatMessage.class);
    }
}