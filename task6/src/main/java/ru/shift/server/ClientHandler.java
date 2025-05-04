package ru.shift.server;

import ru.shift.common.ChatMessage;
import ru.shift.common.JsonUtil;
import ru.shift.common.MessageType;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Log4j2
public class ClientHandler implements Runnable, AutoCloseable {

    private final Socket socket;
    private final ChatServer server;
    private BufferedReader in;
    private PrintWriter out;
    
    private String username;
    private boolean registered = false;

    public ClientHandler(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            in = createReader();
            out = createWriter();
            
            if (!processJoinRequest()) {
                return;
            }
            
            processMessageLoop();
        } catch (IOException e) {
            log.error("Соединение пользователя {} было закрыто, так как произошла ошибка", username, e);
        } catch (Exception e) {
            log.error("Ошибка обработки клиента", e);
        } finally {
            cleanup();
        }
    }

    private BufferedReader createReader() throws IOException {
        return new BufferedReader(
                new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
    }
    
    private PrintWriter createWriter() throws IOException {
        return new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
    }
    
    private boolean processJoinRequest() throws IOException {
        ChatMessage join = JsonUtil.fromJson(in.readLine());

        if (join == null || join.getType() != MessageType.JOIN) {
            sendAndClose(createErrorMessage("Ожидалось сообщение JOIN"));
            return false;
        }

        String candidate = join.getSender();
        if (!server.registerClient(candidate, this)) {
            sendAndClose(createErrorMessage("Ник \"" + candidate + "\" уже занят"));
            return false;
        }

        this.username = candidate;
        this.registered = true;
        notifyUserJoined();
        return true;
    }
    
    private void notifyUserJoined() {
        server.broadcast(new ChatMessage(
                MessageType.JOIN, 
                null,
                "Пользователь " + username + " присоединился",
                LocalDateTime.now()));
        server.broadcastUserList();
    }
    
    private void processMessageLoop() throws IOException {
        String line;
        while ((line = in.readLine()) != null) {
            processMessage(line);
        }
    }
    
    private void processMessage(String jsonMessage) throws IOException {
        ChatMessage msg = JsonUtil.fromJson(jsonMessage);
        if (msg.getType() == MessageType.TEXT) {
            msg.setTimestamp(LocalDateTime.now());
            server.broadcast(msg);
        }
    }

    public void sendRaw(String json) {
        try {
            out.println(json);
        } catch (Exception e) {
            log.warn("Пользователь {} не смог отправить сообщение", username);
        }
    }

    private void sendAndClose(ChatMessage msg) throws IOException {
        sendRaw(JsonUtil.toJson(msg));
        close();
    }

    private static ChatMessage createErrorMessage(String text) {
        return new ChatMessage(MessageType.ERROR, null, text, LocalDateTime.now());
    }

    private void cleanup() {
        close();
        if (registered) {
            server.unregisterClient(username);
        }
    }

    @Override
    public void close() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            log.warn("Произошла ошибка во время закрытия соединения", e);
        }
    }
}