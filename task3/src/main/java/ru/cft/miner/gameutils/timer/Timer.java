package ru.cft.miner.gameutils.timer;

import ru.cft.miner.model.GameStatus;
import ru.cft.miner.model.listener.GameStatusListener;
import ru.cft.miner.model.listener.TimerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Timer implements GameStatusListener {

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final AtomicInteger totalSeconds = new AtomicInteger(0);
    private ScheduledFuture<?> runningTimer;

    private final List<TimerListener> timerListeners = new ArrayList<>();

    public void start() {
        runningTimer = executor.scheduleAtFixedRate(() -> notifyTimerListeners(totalSeconds.incrementAndGet()),
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

    public void registerObserver(TimerListener listener) {
        timerListeners.add(listener);
    }

    public int getTime() {
        return totalSeconds.get();
    }

    @Override
    public void onGameStatusChanged(GameStatus gameStatus) {
        switch (gameStatus) {
            case INITIALIZED -> {
                stop();
                reset();
            }
            case STARTED -> start();
            case WON, LOST -> stop();
        }
    }

    private void notifyTimerListeners(int time) {
        timerListeners.forEach(e -> e.onTimeChanged(time));
    }
}
