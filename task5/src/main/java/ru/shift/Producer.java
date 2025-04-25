package ru.shift;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class Producer implements Runnable {

    private final int id;
    private final ResourceStorage storage;
    private final long producerTime;

    private static int resourceCounter = 0;
    
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Resource resource = createResource();
                try {
                    storage.put(resource, id);
                    Thread.sleep(producerTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        } finally {
            log.info("Производитель {} завершил работу", id);
        }
    }
    
    private synchronized Resource createResource() {
        Resource resource = new Resource(++resourceCounter);
        log.info("Производитель {} произвел ресурс {}", id, resource.id());

        return resource;
    }
}