package ru.cft.miner.model.timer;

import ru.cft.miner.model.observer.TimerListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Timer {

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private TimerListener listener;
    private int totalSeconds;
    private ScheduledFuture<?> future;

    public void start() {
        totalSeconds = 0;
        future = executor.scheduleAtFixedRate(() -> listener.onTimeChanged(++totalSeconds), 1, 1, TimeUnit.SECONDS);
    }

    public void stop() {
        future.cancel(true);
    }

    public void setListener(TimerListener listener) {
        this.listener = listener;
    }
}
