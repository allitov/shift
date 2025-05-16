package ru.shift.client;

import ru.shift.common.ChatMessage;
import ru.shift.common.JsonUtil;
import ru.shift.common.MessageType;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Log4j2
public class ChatClient implements AutoCloseable {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    @Getter
    private String username;
    private final Consumer<ChatMessage> messageConsumer;
    private final ExecutorService pool;

    public ChatClient(Consumer<ChatMessage> messageConsumer) {
        this.messageConsumer = messageConsumer;
        this.pool = Executors.newSingleThreadExecutor();
    }

    public boolean connect(String host, int port, String username) {
        this.username = username;

        CompletableFuture<Boolean> connectionResult = new CompletableFuture<>();

        pool.submit(() -> {
            try (var socket = new Socket(host, port); var in = createReader(socket); var out = createWriter(socket)) {
                this.socket = socket;
                this.in = in;
                this.out = out;

                ChatMessage join = new ChatMessage(
                        MessageType.JOIN, username, null, LocalDateTime.now());
                sendMessage(join);

                ChatMessage response = JsonUtil.fromJson(in.readLine());
                messageConsumer.accept(response);
                if (response.getType() == MessageType.ERROR) {
                    connectionResult.complete(false);
                    return;
                }
                connectionResult.complete(true);

                readLoop();
            } catch (IOException e) {
                log.error("Не удалось подключиться", e);
                connectionResult.completeExceptionally(e);
            }
        });

        try {
            return connectionResult.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Не удалось подключиться", e);
            return false;
        }
    }

    public void sendText(String text) {
        ChatMessage msg = new ChatMessage(
                MessageType.TEXT, username, text, LocalDateTime.now());
        sendMessage(msg);
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
        pool.shutdownNow();
    }

    private BufferedReader createReader(Socket socket) throws IOException {
        return new BufferedReader(
                new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
    }

    private PrintWriter createWriter(Socket socket) throws IOException {
        return new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
    }

    private void sendMessage(ChatMessage msg) {
        try {
            out.println(JsonUtil.toJson(msg));
        } catch (IOException e) {
            log.warn("Ошибка отправки сообщения", e);
            showError("Ошибка отправки сообщения. Попробуйте переподключиться");
        }
    }

    private void readLoop() {
        try {
            String line;
            while ((line = in.readLine()) != null) {
                ChatMessage msg = JsonUtil.fromJson(line);
                messageConsumer.accept(msg);
            }
        } catch (IOException e) {
            log.warn("Произошла ошибка во время чтения данных", e);
            showError("Ошибка получения новых сообщений. Попробуйте переподключиться");
        }
    }

    private void showError(String content) {
        ChatMessage errorMsg = new ChatMessage(
                MessageType.ERROR,
                null,
                content,
                LocalDateTime.now());
        messageConsumer.accept(errorMsg);
    }
}