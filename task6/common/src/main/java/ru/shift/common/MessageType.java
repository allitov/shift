package ru.shift.common;

public enum MessageType {

    JOIN,          // пользователь входит
    TEXT,          // обычный текст
    LEAVE,         // пользователь выходит
    USER_LIST,     // сервер присылает актуальный список пользователей
    ERROR          // сервер сообщает об ошибке (например, ник занят)
}