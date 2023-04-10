package com.evheniy.botconstruct;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;

public class BotConstructor {
    public static TelegramBot createBot(String token, UpdatesListener updatesListener) {
        TelegramBot bot = new TelegramBot(token);
        bot.setUpdatesListener(updatesListener);
        return bot;
    }
}
