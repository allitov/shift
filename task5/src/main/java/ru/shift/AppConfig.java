package ru.shift;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Log4j2
@Getter
public class AppConfig {
    
    private int producerCount;
    private int consumerCount;
    private long producerTime;
    private long consumerTime;
    private int storageSize;

    public AppConfig() {
        loadConfig();
    }

    private void loadConfig() {
        Properties config = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                log.error("Не удалось загрузить config.properties");
                System.exit(1);
            }
            config.load(input);
            producerCount = Integer.parseInt(config.getProperty("producerCount", "2"));
            consumerCount = Integer.parseInt(config.getProperty("consumerCount", "3"));
            producerTime = Long.parseLong(config.getProperty("producerTime", "1000"));
            consumerTime = Long.parseLong(config.getProperty("consumerTime", "1500"));
            storageSize = Integer.parseInt(config.getProperty("storageSize", "5"));
        } catch (IOException ex) {
            log.error("Ошибка во время чтения config.properties");
            System.exit(1);
        }
    }
}