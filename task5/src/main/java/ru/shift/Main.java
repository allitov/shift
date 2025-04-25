package ru.shift;

public class Main {

    private static int producerIdCounter = 0;
    private static int consumerIdCounter = 0;
    private static ResourceStorage storage;
    private static AppConfig config;

    public static void main(String[] args) {
        config = new AppConfig();
        storage = new ResourceStorage(config.getStorageSize());
        createProducers();
        createConsumers();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.exit(0);
    }

    private static void createProducers() {
        for (int i = 0; i < config.getProducerCount(); i++) {
            new Thread(new Producer(
                    ++producerIdCounter,
                    storage,
                    config.getProducerTime()
            )).start();
        }
    }

    private static void createConsumers() {
        for (int i = 0; i < config.getConsumerCount(); i++) {
            new Thread(new Consumer(
                    ++consumerIdCounter,
                    storage,
                    config.getConsumerTime()
            )).start();
        }
    }
}