package com.evheniy.botconstruct.botshandler.impl;

import com.evheniy.botconstruct.AppBot;
import com.evheniy.botconstruct.model.BotsData;
import com.evheniy.botconstruct.model.Message;
import com.evheniy.botconstruct.model.BotUser;
import com.evheniy.botconstruct.repository.MessageRepository;
import com.evheniy.botconstruct.repository.BotUserRepository;
import com.viber.bot.event.callback.OnMessageReceived;
import com.viber.bot.event.incoming.IncomingMessageEvent;
import com.viber.bot.message.TextMessage;
import com.viber.bot.*;

import java.util.Optional;

public class ViberBaseUpdateHandler implements OnMessageReceived {

    private final BotsData botsData;
    private final AppBot appBot;


    public ViberBaseUpdateHandler(BotsData botsData, AppBot appBot) {
        this.botsData = botsData;
        this.appBot = appBot;
    }

    @Override
    public void messageReceived(IncomingMessageEvent event, com.viber.bot.message.Message message, Response response) {
        BotUserRepository botUserRepository = appBot.getBotUserRepository();
        MessageRepository messageRepository = appBot.getMessageRepository();

        String senderId = event.getSender().getId();
        Optional<BotUser> userOptional = botUserRepository.findByChatId(Long.valueOf(senderId));

        if (userOptional.isEmpty()) {
            BotUser botUser = new BotUser();
            botUser.setChatId(Long.valueOf(senderId));
            botUser.setFirstName(event.getSender().getName());
            botUserRepository.save(botUser);

            Message savedMessage = new Message();
            savedMessage.setContent(((TextMessage) message).getText());
            savedMessage.setBotUser(botUser);
            savedMessage.setBotsData(botsData);
            messageRepository.save(savedMessage);
        } else {
            BotUser botUser = userOptional.get();
            Message savedMessage = new Message();
            savedMessage.setContent(((TextMessage) message).getText());
            savedMessage.setBotUser(botUser);
            savedMessage.setBotsData(botsData);
            messageRepository.save(savedMessage);
        }

        // reply to user
        TextMessage replyMessage = new TextMessage(((TextMessage) message).getText());
        response.send(replyMessage);
    }
}
