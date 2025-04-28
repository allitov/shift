package ru.shift;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Log4j2
@RequiredArgsConstructor
public class Task {
    
    private final long start;
    private final long end;
    private final long threshold;
    private final Formula formula;

    public Double compute(ExecutorService executor) {
        return executeParallel(executor);
    }
    
    private Double executeParallel(ExecutorService executor) {
        long totalElements = end - start + 1;
        long numberOfTasks = Math.max(1, totalElements / threshold);
        
        List<CompletableFuture<Double>> futures = new ArrayList<>();

        for (long i = 0; i < numberOfTasks; i++) {
            long taskStart = start + i * threshold;
            long taskEnd = Math.min(end, taskStart + threshold - 1);

            CompletableFuture<Double> future = CompletableFuture.supplyAsync(() -> {
                double sum = 0;
                for (long n = taskStart; n <= taskEnd; n++) {
                    sum += formula.calculate(n);
                }
                log.info("Start: {}, End: {}, Result: {}", taskStart, taskEnd, sum);
                return sum;
            }, executor);
            
            futures.add(future);
        }

        return futures.stream()
                .map(CompletableFuture::join)
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}