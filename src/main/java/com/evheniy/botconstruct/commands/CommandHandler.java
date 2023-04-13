package com.evheniy.botconstruct.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;

public interface CommandHandler {
    boolean canHandle(String command);
    void handle(TelegramBot bot, Update update);
}