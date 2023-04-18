package com.evheniy.botconstruct;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;

public class TelegramBotAdapter implements Bot {
    private TelegramBot bot;

    public TelegramBotAdapter(String token) {
        bot = new TelegramBot(token);
    }

    @Override
    public void setUpdatesListener(UpdatesListener listener) {
        bot.setUpdatesListener(listener);
    }

    @Override
    public void sendMessage(Long chatId, String message) {
        bot.execute(new SendMessage(chatId, message));
    }

    // Другие методы, необходимые для работы с ботом Telegram
}