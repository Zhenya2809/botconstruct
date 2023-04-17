package com.evheniy.botconstruct.commands;

import com.evheniy.botconstruct.ExecutionContext;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;

public interface CommandHandler {
    boolean canHandle(String command);

    void handle(ExecutionContext context);

    String typeCommand();
}