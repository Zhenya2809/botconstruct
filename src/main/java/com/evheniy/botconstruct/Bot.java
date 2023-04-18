package com.evheniy.botconstruct;

import com.pengrad.telegrambot.UpdatesListener;

public interface Bot {
    void setUpdatesListener(UpdatesListener listener);
    void sendMessage(Long chatId, String message);
    // Другие методы, необходимые для работы с ботом
}
