package ru.shift;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayDeque;
import java.util.Queue;

@Log4j2
public class ResourceStorage {

    private final Queue<Resource> storage;
    private final int maxSize;

    public ResourceStorage(int maxSize) {
        this.storage = new ArrayDeque<>(maxSize);
        this.maxSize = maxSize;
    }

    public synchronized int put(Resource resource, int producerId) throws InterruptedException {
        while (storage.size() == maxSize) {
            log.debug("Производитель {} ожидает: склад полон", producerId);
            wait();
        }
        storage.offer(resource);
        notifyAll();

        return storage.size();
    }

    public synchronized Resource take(int consumerId) throws InterruptedException {
        while (storage.isEmpty()) {
            log.debug("Потребитель {} ожидает: склад пуст", consumerId);
            wait();
        }
        Resource resource = storage.poll();
        log.debug("Потребитель {} забрал ресурс {}. Ресурсов на складе: {}",
                consumerId, resource.id(), storage.size());
        notifyAll();

        return resource;
    }
}