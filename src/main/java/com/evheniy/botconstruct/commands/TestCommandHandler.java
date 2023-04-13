package com.evheniy.botconstruct.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class TestCommandHandler implements CommandHandler {
    @Override
    public boolean canHandle(String command) {
        return "/test".equalsIgnoreCase(command);
    }

    @Override
    public void handle(TelegramBot bot, Update update) {
        long chatId = update.message().chat().id();
        bot.execute(new SendMessage(chatId, "Це тестове повідомлення"));
    }

}