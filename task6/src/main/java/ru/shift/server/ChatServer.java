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

    private final int port;
    private final Map<String, ClientHandler> clients = new ConcurrentHashMap<>();
    private final ExecutorService clientThreadPool;
    private ServerSocket serverSocket;
    private volatile boolean isRunning = false;

    public ChatServer(int port) {
        this.port = port;
        this.clientThreadPool = Executors.newCachedThreadPool();
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        isRunning = true;
        
        log.info("Чат-сервер запущен на порту {}", port);
        
        acceptClientsLoop();
    }
    
    private void acceptClientsLoop() throws IOException {
        try {
            while (isRunning) {
                Socket clientSocket = serverSocket.accept();
                log.debug("Новое подключение: {}", clientSocket.getInetAddress());
                handleNewClient(clientSocket);
            }
        } catch (IOException e) {
            if (isRunning) {
                throw e;
            }
        }
    }
    
    private void handleNewClient(Socket clientSocket) {
        ClientHandler handler = new ClientHandler(clientSocket, this);
        clientThreadPool.execute(handler);
    }

    public boolean registerClient(String username, ClientHandler handler) {
        if (username == null || username.trim().isEmpty()) {
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
    
    private void notifyUserLeft(String username) {
        broadcast(createSystemMessage(
                MessageType.LEAVE,
                "Пользователь " + username + " покинул чат"));
        broadcastUserList();
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
    
    private void broadcastRaw(String json) {
        for (ClientHandler handler : clients.values()) {
            handler.sendRaw(json);
        }
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
    
    private static ChatMessage createSystemMessage(MessageType type, String content) {
        return new ChatMessage(type, null, content, LocalDateTime.now());
    }

    @Override
    public void close() {
        stop();
        log.info("Чат-сервер остановлен");
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
}