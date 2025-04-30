package ru.shift;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class Main {

    private static final List<Thread> allThreads = new ArrayList<>();

    public static void main(String[] args) {
        var config = new AppConfig();
        try {
            config = new AppConfig();
        } catch (RuntimeException ex) {
            System.exit(1);
        }

        var storage = new ResourceStorage(config.getStorageSize());

        createProducers(config, storage);
        createConsumers(config, storage);

        try {
            Thread.sleep(config.getWorkTime());
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
        for (int i = 0; i < config.getProducerCount(); i++) {
            Thread thread = new Thread(new Producer(
                    ++producerIdCounter,
                    storage,
                    config.getProducerTime()
            ), "Producer-" + producerIdCounter);
            allThreads.add(thread);
            thread.start();
        }
    }

    private static void createConsumers(AppConfig config, ResourceStorage storage) {
        int consumerIdCounter = 0;
        for (int i = 0; i < config.getConsumerCount(); i++) {
            Thread thread = new Thread(new Consumer(
                    ++consumerIdCounter,
                    storage,
                    config.getConsumerTime()
            ), "Consumer-" + consumerIdCounter);
            allThreads.add(thread);
            thread.start();
        }
    }
}