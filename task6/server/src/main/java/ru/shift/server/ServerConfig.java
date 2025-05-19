package ru.shift.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;

public record ServerConfig(int port) {

    private static final String PROPERTIES_FILE = "server.properties";

    public static ServerConfig load() {
        Properties props = loadProperties();

        return new ServerConfig(
                parseIntParam(props, "port", 9000)
        );
    }

    private static Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream input = ServerConfig.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new FileNotFoundException("Could not find " + PROPERTIES_FILE);
            }
            props.load(input);
        } catch (IOException e) {
            throw new UncheckedIOException("Error loading configuration", e);
        }

        return props;
    }

    private static int parseIntParam(Properties props, String key, int defaultValue) {
        return parseNumberParam(props, key, defaultValue, Integer::parseInt);
    }

    private static <T extends Number> T parseNumberParam(Properties props, String key, T defaultValue,
                                                         Function<String, T> parser) {
        return Optional.ofNullable(props.getProperty(key))
                .map(value -> {
                    try {
                        return parser.apply(value);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid value for " + key + ": " + value);
                    }
                })
                .orElse(defaultValue);
    }
}