package ru.shift;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Producer implements Runnable {

    private final int id;
    private final ResourceStorage storage;
    private final long producerTime;
    private static int resourceCounter = 0;

    public Producer(int id, ResourceStorage storage, long producerTime) {
        this.id = id;
        this.storage = storage;
        this.producerTime = producerTime;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(producerTime);
                Resource resource = createResource();
                storage.put(resource, id);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.warn("Производитель {} прерван.", id);
                break;
            }
        }
        log.info("Производитель {} завершил работу.", id);
    }

    private synchronized Resource createResource() {
        Resource resource = new Resource(++resourceCounter);
        log.info("Производитель {} произвел ресурс {}.", id, resource.id());

        return resource;
    }
}