package ru.shift;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.RecursiveTask;

@Log4j2
@RequiredArgsConstructor
public class Task extends RecursiveTask<Double> {
    
    private final long start;
    private final long end;
    private final long threshold;
    private final Formula formula;

    @Override
    protected Double compute() {
        if (end - start <= threshold) {
            return computeDirectly();
        } else {
            return splitAndCompute();
        }
    }
    
    private Double computeDirectly() {
        double sum = 0;
        for (long n = start; n <= end; n++) {
            sum += formula.calculate(n);
        }
        log.info("Start: {}, End: {}, Result: {}", start, end, sum);

        return sum;
    }
    
    private Double splitAndCompute() {
        long mid = (start + end) / 2;
        
        Task leftTask = new Task(start, mid, threshold, formula);
        Task rightTask = new Task(mid + 1, end, threshold, formula);

        leftTask.fork();
        Double rightResult = rightTask.compute();
        Double leftResult = leftTask.join();
        
        return leftResult + rightResult;
    }
}