package ru.shift;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Log4j2
public class Main {

    public static void main(String[] args) {
        AppConfig config = null;
        try {
            config = AppConfig.load();
        } catch (RuntimeException ex) {
            log.error("Произошла ошибка во время чтения конфигурации: ", ex);
            System.exit(1);
        }
        ResourceStorage storage = new ResourceStorage(config.storageSize());
        List<Thread> workers = new ArrayList<>(config.consumerCount() + config.producerCount());

        startWorkers(config, storage, workers);

        waitForWorkCompletion(config.workTime());

        shutdownWorkers(workers);

        log.info("Все потоки успешно завершены");
    }

    private static void startWorkers(AppConfig config, ResourceStorage storage, List<Thread> workers) {
        workers.addAll(createWorkerThreads("Producer", config.producerCount(),
                id -> new Producer(id, storage, config.producerTime())));
        workers.addAll(createWorkerThreads("Consumer", config.consumerCount(),
                id -> new Consumer(id, storage, config.consumerTime())));
    }

    private static <T extends Runnable> List<Thread> createWorkerThreads(String workerType, int count,
                                                                         Function<Integer, T> workerFactory) {
        List<Thread> workers = new ArrayList<>(count);
        for (int id = 1; id <= count; id++) {
            Thread worker = new Thread(workerFactory.apply(id), workerType + "-" + id);
            workers.add(worker);
            worker.start();
        }

        return workers;
    }

    private static void waitForWorkCompletion(long workTime) {
        try {
            Thread.sleep(workTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void shutdownWorkers(List<Thread> workers) {
        for (Thread worker : workers) {
            worker.interrupt();
        }

        for (Thread worker : workers) {
            try {
                worker.join(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}