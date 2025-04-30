package ru.shift;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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

        startThreads(config, storage);

        waitForWorkCompletion(config.workTime());

        shutdownThreads();

        log.info("Все потоки успешно завершены");
    }

    private static void startThreads(AppConfig config, ResourceStorage storage) {
        createWorkerThreads("Producer", config.producerCount(),
                id -> new Producer(id, storage, config.producerTime()));
        createWorkerThreads("Consumer", config.consumerCount(),
                id -> new Consumer(id, storage, config.consumerTime()));
    }

    private static <T extends Runnable> void createWorkerThreads(String threadType, int count,
                                                                 Function<Integer, T> workerFactory) {
        for (int id = 1; id <= count; id++) {
            Thread thread = new Thread(workerFactory.apply(id), threadType + "-" + id);
            allThreads.add(thread);
            thread.start();
        }
    }

    private static void waitForWorkCompletion(long workTime) {
        try {
            Thread.sleep(workTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void shutdownThreads() {
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
    }
}