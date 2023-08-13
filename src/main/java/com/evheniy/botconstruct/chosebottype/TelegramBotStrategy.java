package com.evheniy.botconstruct.chosebottype;

import com.evheniy.botconstruct.AppBot;
import com.evheniy.botconstruct.BotConstructor;
import com.evheniy.botconstruct.MyUpdatesListener;
import com.evheniy.botconstruct.botshandler.impl.TelegramBaseUpdateHandler;
import com.evheniy.botconstruct.model.BotsData;
import com.pengrad.telegrambot.TelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TelegramBotStrategy implements BotStrategy {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public TelegramBotStrategy(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void setupBot(BotsData botsData, AppBot appBot) {
        TelegramBot bot = BotConstructor.createBot(botsData.getToken(), null);
        TelegramBaseUpdateHandler telegramUpdateHandler = new TelegramBaseUpdateHandler(messagingTemplate);
        telegramUpdateHandler.setBotsData(botsData);
        telegramUpdateHandler.setBotUserRepository(appBot.getBotUserRepository());
        telegramUpdateHandler.setChatQueueRepository(appBot.getChatQueueRepository());
        telegramUpdateHandler.setMessageRepository(appBot.getMessageRepository());
        telegramUpdateHandler.setCommandRepository(appBot.getCommandRepository());
        telegramUpdateHandler.setBot(bot);
        MyUpdatesListener updatesListener = new MyUpdatesListener(telegramUpdateHandler);
        updatesListener.setBot(bot);
        updatesListener.setBotsData(botsData);
        updatesListener.setCommandRepository(appBot.getCommandRepository());
        updatesListener.setBotUserRepository(appBot.getBotUserRepository());
        updatesListener.setChatQueueRepository(appBot.getChatQueueRepository());
        updatesListener.setMessageRepository(appBot.getMessageRepository());
        bot.setUpdatesListener(updatesListener);
        log.info("telegram bot started");
    }
}


