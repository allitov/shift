package ru.cft.miner.gameutils.timer;

import ru.cft.miner.model.GameModel;
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

/**
 * Класс таймера игры, отвечающий за отсчет времени и уведомление слушателей
 * о его изменении. Реагирует на изменения статуса игры.
 */
public class Timer implements GameStatusListener {

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final AtomicInteger totalSeconds = new AtomicInteger(0);
    private ScheduledFuture<?> runningTimer;

    private final List<TimerListener> timerListeners = new ArrayList<>();

    /**
     * Создает новый таймер и регистрирует его как слушателя событий модели
     *
     * @param model игровая модель
     */
    public Timer(GameModel model) {
        registerAsModelObserver(model);
    }

    /**
     * Запускает таймер, который обновляется каждую секунду
     * и уведомляет слушателей об изменении времени
     */
    public void start() {
        runningTimer = executor.scheduleAtFixedRate(() -> notifyTimerListeners(totalSeconds.incrementAndGet()),
                1, 1, TimeUnit.SECONDS
        );
    }

    /**
     * Сбрасывает таймер в нулевое значение
     */
    public void reset() {
        totalSeconds.set(0);
    }

    /**
     * Останавливает работу таймера
     */
    public void stop() {
        if (runningTimer != null) {
            runningTimer.cancel(true);
        }
    }

    /**
     * Регистрирует слушателя изменений времени
     *
     * @param listener слушатель таймера
     */
    public void registerObserver(TimerListener listener) {
        timerListeners.add(listener);
    }

    /**
     * Возвращает текущее время в секундах
     *
     * @return время в секундах
     */
    public int getTime() {
        return totalSeconds.get();
    }

    /**
     * Обрабатывает изменение статуса игры: 
     * - при инициализации останавливает и сбрасывает таймер
     * - при старте запускает таймер
     * - при завершении (победа или поражение) останавливает таймер
     *
     * @param gameStatus новый статус игры
     */
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

    /**
     * Уведомляет всех слушателей об изменении времени
     *
     * @param time текущее время в секундах
     */
    private void notifyTimerListeners(int time) {
        timerListeners.forEach(e -> e.onTimeChanged(time));
    }

    /**
     * Регистрирует представление как слушателя различных событий модели
     *
     * @param model игровая модель
     */
    private void registerAsModelObserver(GameModel model) {
        model.registerObserver(this);
    }
}