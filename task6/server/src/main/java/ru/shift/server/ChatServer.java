package ru.shift.server;

import ru.shift.common.ChatMessage;
import ru.shift.common.JsonUtil;
import ru.shift.common.MessageType;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log4j2
public class ChatServer implements AutoCloseable {

    private static final int DEFAULT_PORT = 9000;

    private final int port;
    private final Map<String, ClientHandler> clients = new ConcurrentHashMap<>();
    private final ExecutorService clientThreadPool;
    private ServerSocket serverSocket;
    private volatile boolean isRunning = false;

    public ChatServer(int port) {
        this.port = setupPort(port);
        this.clientThreadPool = Executors.newCachedThreadPool();
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        isRunning = true;

        log.info("Чат-сервер запущен на порту {}", port);

        acceptClientsLoop();
    }

    public void broadcastUserList() {
        Set<String> usernames = Collections.unmodifiableSet(clients.keySet());

        ChatMessage listMessage = new ChatMessage(
                MessageType.USER_LIST,
                null,
                String.join(",", usernames),
                LocalDateTime.now()
        );

        broadcast(listMessage);
    }

    public boolean registerClient(String username, ClientHandler handler) {
        if (!isNameValid(username)) {
            return false;
        }

        boolean result = clients.putIfAbsent(username, handler) == null;

        if (result) {
            log.debug("Пользователь {} зарегистрирован", username);
        } else {
            log.debug("Не удалось зарегистрировать пользователя {}: имя занято", username);
        }

        return result;
    }

    public void unregisterClient(String username) {
        if (username == null) {
            return;
        }

        ClientHandler removed = clients.remove(username);

        if (removed != null) {
            log.debug("Пользователь {} покинул чат", username);
            notifyUserLeft(username);
        }
    }

    public void broadcast(ChatMessage message) {
        if (message == null) {
            return;
        }

        try {
            String json = JsonUtil.toJson(message);
            broadcastRaw(json);
        } catch (Exception e) {
            log.error("Ошибка сериализации сообщения", e);
        }
    }

    @Override
    public void close() {
        stop();
        log.info("Чат-сервер остановлен");
    }

    private int setupPort(int port) {
        if (!isPortValid(port)) {
            log.warn("Неверный порт {}. Сервер использует порт по умолчанию {}", port, DEFAULT_PORT);
            return DEFAULT_PORT;
        }

        return port;
    }

    private void acceptClientsLoop() {
        try {
            while (isRunning) {
                Socket clientSocket = serverSocket.accept();
                log.debug("Новое подключение: {}", clientSocket.getInetAddress());
                handleNewClient(clientSocket);
            }
        } catch (IOException e) {
            if (isRunning) {
                log.error("Не получилось установить новое подключение", e);
            }
        }
    }

    private void handleNewClient(Socket clientSocket) {
        ClientHandler handler = new ClientHandler(clientSocket, this);
        clientThreadPool.execute(handler);
    }

    private void notifyUserLeft(String username) {
        broadcast(createSystemMessage(
                MessageType.LEAVE,
                "Пользователь " + username + " покинул чат"));
        broadcastUserList();
    }

    private void broadcastRaw(String json) {
        for (ClientHandler handler : clients.values()) {
            handler.sendRaw(json);
        }
    }

    private static ChatMessage createSystemMessage(MessageType type, String content) {
        return new ChatMessage(type, null, content, LocalDateTime.now());
    }

    private void stop() {
        isRunning = false;

        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            log.warn("Произошла ошибка во время закрытия соединения", e);
        }

        clientThreadPool.shutdownNow();
    }

    private boolean isPortValid(int port) {
        return port > 0 && port <= 65535;
    }

    private boolean isNameValid(String username) {
        return username != null && !username.isBlank();
    }
}