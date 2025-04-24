package ru.shift;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.ForkJoinPool;

@Log4j2
public class Main {

    private static final int THREADS_AVAILABLE = Runtime.getRuntime().availableProcessors();
    private static final long TASKS_PER_THREAD = 8;
    private static final Formula FORMULA = n -> 1. / (n * (n + 1)); // sum = 1

    public static void main(String[] args) {
        long number = InputUtil.readLong();
        long threshold = number / (THREADS_AVAILABLE * TASKS_PER_THREAD);
        Task task = new Task(1, number, threshold, FORMULA);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREADS_AVAILABLE);

        log.info("Starting calculations");
        long start = System.currentTimeMillis();
        double result = forkJoinPool.invoke(task);
        long end = System.currentTimeMillis();

        log.info("Result: {}, Total time: {} ms", result, end - start);
        forkJoinPool.shutdown();
    }
}