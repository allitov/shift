package ru.shift;

import lombok.extern.log4j.Log4j2;

import java.util.LinkedList;
import java.util.Queue;

@Log4j2
public class ResourceStorage {

    private final Queue<Resource> storage = new LinkedList<>();
    private final int maxSize;

    public ResourceStorage(int maxSize) {
        this.maxSize = maxSize;
    }

    public synchronized void put(Resource resource, int producerId) throws InterruptedException {
        while (storage.size() == maxSize) {
            log.info("Производитель {} ожидает: склад полон.", producerId);
            wait();
        }
        storage.offer(resource);
        log.info("Производитель {} доставил ресурс {}. Ресурсов на складе: {}",
                producerId, resource.id(), storage.size());
        notifyAll();
    }

    public synchronized Resource take(int consumerId) throws InterruptedException {
        while (storage.isEmpty()) {
            log.info("Потребитель {} ожидает: склад пуст.", consumerId);
            wait();
        }
        Resource resource = storage.poll();
        log.info("Потребитель {} забрал ресурс {}. Ресурсов на складе: {}",
                consumerId, resource.id(), storage.size());
        notifyAll();

        return resource;
    }
}