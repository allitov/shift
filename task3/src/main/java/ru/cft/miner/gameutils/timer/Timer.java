package ru.cft.miner.gameutils.timer;

import ru.cft.miner.model.listener.TimerListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Timer {

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final AtomicInteger totalSeconds = new AtomicInteger(0);
    private TimerListener listener;
    private ScheduledFuture<?> runningTimer;

    public void start() {
        runningTimer = executor.scheduleAtFixedRate(() -> listener.onTimeChanged(totalSeconds.incrementAndGet()),
                1, 1, TimeUnit.SECONDS
        );
    }

    public void reset() {
        totalSeconds.set(0);
    }

    public void stop() {
        if (runningTimer != null) {
            runningTimer.cancel(true);
        }
    }

    public void setListener(TimerListener listener) {
        this.listener = listener;
    }

    public int getTime() {
        return totalSeconds.get();
    }
}
