package ru.shift;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
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
        ForkJoinPool forkJoinPool = null;
        
        try {
            forkJoinPool = new ForkJoinPool(THREADS_AVAILABLE);
            
            log.info("Starting calculations");
            long start = System.currentTimeMillis();
            
            double result = forkJoinPool.invoke(task);
            
            long executionTime = System.currentTimeMillis() - start;
            log.info("Result: {}, Total time: {} ms", result, executionTime);
        } finally {
            shutdownPool(forkJoinPool);
        }
    }

    private static void shutdownPool(ExecutorService pool) {
        if (pool != null) {
            pool.shutdown();
            try {
                if (!pool.awaitTermination(10, TimeUnit.SECONDS)) {
                    pool.shutdownNow();
                }
            } catch (InterruptedException e) {
                pool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}