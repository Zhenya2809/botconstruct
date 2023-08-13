package com.evheniy.botconstruct;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;

public class BotConstructor {
    public static TelegramBot createBot(String token, UpdatesListener updatesListener) {
        TelegramBot bot = new TelegramBot(token);
        bot.setUpdatesListener(updatesListener);
        return bot;
    }
}
