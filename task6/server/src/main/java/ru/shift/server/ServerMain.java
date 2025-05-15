package ru.shift.server;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class ServerMain {

    public static void main(String[] args) {
        ServerConfig config = null;
        try {
            config = ServerConfig.load();
        } catch (RuntimeException e) {
            log.error("Произошла ошибка во время чтения конфигурации", e);
            System.exit(1);
        }

        try (ChatServer server = new ChatServer(config.port())) {
            server.start();
        } catch (IOException e) {
            log.error("Сервер был остановлен", e);
        }
    }
}