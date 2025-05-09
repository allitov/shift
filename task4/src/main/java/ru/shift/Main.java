package ru.shift;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Log4j2
public class Main {

    private static final int THREADS_AVAILABLE = Runtime.getRuntime().availableProcessors();
    private static final long TASKS_PER_THREAD = 8;
    private static final Formula FORMULA = n -> 1. / (n * (n + 1)); // sum = 1

    public static void main(String[] args) {
        long number = InputUtil.readLong();
        long threshold = calculateThreshold(number);
        Task task = new Task(1, number, threshold, FORMULA);
        
        executeTask(task);
    }

    private static long calculateThreshold(long totalElements) {
        return Math.max(1, totalElements / (THREADS_AVAILABLE * TASKS_PER_THREAD));
    }

    private static void executeTask(Task task) {
        ExecutorService executor = null;
        
        try {
            executor = Executors.newFixedThreadPool(THREADS_AVAILABLE);
            
            log.info("Starting calculations");
            long start = System.currentTimeMillis();
            
            Double result = task.compute(executor);
            
            long executionTime = System.currentTimeMillis() - start;
            log.info("Result: {}, Total time: {} ms", result, executionTime);
        } finally {
            shutdownPool(executor);
        }
    }

    private static void shutdownPool(ExecutorService executor) {
        if (executor != null) {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}