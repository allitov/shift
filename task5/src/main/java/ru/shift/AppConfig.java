package ru.shift;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;

public record AppConfig(int producerCount, int consumerCount, long producerTime,
                        long consumerTime, int storageSize, long workTime) {

    private static final String PROPERTIES_FILE = "config.properties";

    public static AppConfig load() {
        Properties props = loadProperties();

        return new AppConfig(
                parseIntParam(props, "producerCount", 2),
                parseIntParam(props, "consumerCount", 3),
                parseLongParam(props, "producerTime", 1000),
                parseLongParam(props, "consumerTime", 1500),
                parseIntParam(props, "storageSize", 5),
                parseLongParam(props, "workTime", 10000)
        );
    }

    private static Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream input = AppConfig.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new FileNotFoundException("Could not find " + PROPERTIES_FILE);
            }
            props.load(input);
        } catch (IOException ex) {
            throw new UncheckedIOException("Error loading configuration", ex);
        }

        return props;
    }

    private static int parseIntParam(Properties props, String key, int defaultValue) {
        return parseNumberParam(props, key, defaultValue, Integer::parseInt);
    }

    private static long parseLongParam(Properties props, String key, long defaultValue) {
        return parseNumberParam(props, key, defaultValue, Long::parseLong);
    }

    private static <T extends Number> T parseNumberParam(Properties props, String key, T defaultValue,
                                                         Function<String, T> parser) {
        return Optional.ofNullable(props.getProperty(key))
                .map(value -> {
                    try {
                        return parser.apply(value);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Parameter " + key + " has wrong value: " + value);
                    }
                })
                .orElse(defaultValue);
    }
}