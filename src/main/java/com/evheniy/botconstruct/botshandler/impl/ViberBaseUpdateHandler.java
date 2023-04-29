package com.evheniy.botconstruct.botshandler.impl;

import com.evheniy.botconstruct.AppBot;
import com.evheniy.botconstruct.model.Message;
import com.evheniy.botconstruct.model.User;
import com.evheniy.botconstruct.repository.MessageRepository;
import com.evheniy.botconstruct.repository.UserRepository;
import com.viber.bot.event.callback.OnMessageReceived;
import com.viber.bot.event.incoming.IncomingMessageEvent;
import com.viber.bot.message.TextMessage;
import com.viber.bot.*;

import java.util.Optional;

public class ViberBaseUpdateHandler implements OnMessageReceived {

    private final String authToken;
    private final AppBot appBot;

    public ViberBaseUpdateHandler(String authToken, AppBot appBot) {
        this.authToken = authToken;
        this.appBot = appBot;
    }

    @Override
    public void messageReceived(IncomingMessageEvent event, com.viber.bot.message.Message message, Response response) {
        UserRepository userRepository = appBot.getUserRepository();
        MessageRepository messageRepository = appBot.getMessageRepository();

        String senderId = event.getSender().getId();
        Optional<User> userOptional = userRepository.findByChatId(Long.valueOf(senderId));

        if (userOptional.isEmpty()) {
            User user = new User();
            user.setChatId(Long.valueOf(senderId));
            user.setFirstName(event.getSender().getName());
            userRepository.save(user);

            Message savedMessage = new Message();
            savedMessage.setContent(((TextMessage) message).getText());
            savedMessage.setUser(user);
            savedMessage.setToken(authToken);
            messageRepository.save(savedMessage);
        } else {
            User user = userOptional.get();
            Message savedMessage = new Message();
            savedMessage.setContent(((TextMessage) message).getText());
            savedMessage.setUser(user);
            savedMessage.setToken(authToken);
            messageRepository.save(savedMessage);
        }

        // reply to user
        TextMessage replyMessage = new TextMessage(((TextMessage) message).getText());
        response.send(replyMessage);
    }
}
