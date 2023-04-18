package com.evheniy.botconstruct.Service.impl;

import com.evheniy.botconstruct.Service.ReplyService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;

public class TelegramReplyService implements ReplyService {
    private final TelegramBot bot;
    private final Long chatId;

    public TelegramReplyService(TelegramBot bot, Long chatId) {
        this.bot = bot;
        this.chatId = chatId;
    }

    @Override
    public void reply(String message) {
        bot.execute(new SendMessage(chatId, message));
    }
}
