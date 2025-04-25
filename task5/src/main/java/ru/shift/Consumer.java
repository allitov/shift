package ru.shift;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class Consumer implements Runnable {

    private final int id;
    private final ResourceStorage storage;
    private final long consumerTime;

    @Override
    public void run() {
        while (true) {
            try {
                Resource resource = storage.take(id);
                log.info("Потребитель {} потребил ресурс {}", id, resource.id());
                Thread.sleep(consumerTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.warn("Потребитель {} прерван.", id);
                break;
            }
        }
        log.info("Потребитель {} завершил работу.", id);
    }
}