package ru.shift;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class Main {

    private static final List<Thread> allThreads = new ArrayList<>();

    public static void main(String[] args) {
        AppConfig config = null;
        try {
            config = AppConfig.load();
        } catch (RuntimeException ex) {
            log.error("Произошла ошибка во время чтения конфигурации: ", ex);
            System.exit(1);
        }

        ResourceStorage storage = new ResourceStorage(config.storageSize());

        createProducers(config, storage);
        createConsumers(config, storage);

        try {
            Thread.sleep(config.workTime());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        for (Thread thread : allThreads) {
            thread.interrupt();
        }

        for (Thread thread : allThreads) {
            try {
                thread.join(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        log.info("Все потоки успешно завершены");
    }

    private static void createProducers(AppConfig config, ResourceStorage storage) {
        int producerIdCounter = 0;
        for (int i = 0; i < config.producerCount(); i++) {
            Thread thread = new Thread(new Producer(
                    ++producerIdCounter,
                    storage,
                    config.producerTime()
            ), "Producer-" + producerIdCounter);
            allThreads.add(thread);
            thread.start();
        }
    }

    private static void createConsumers(AppConfig config, ResourceStorage storage) {
        int consumerIdCounter = 0;
        for (int i = 0; i < config.consumerCount(); i++) {
            Thread thread = new Thread(new Consumer(
                    ++consumerIdCounter,
                    storage,
                    config.consumerTime()
            ), "Consumer-" + consumerIdCounter);
            allThreads.add(thread);
            thread.start();
        }
    }
}