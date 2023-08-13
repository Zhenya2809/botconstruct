package com.evheniy.botconstruct.botshandler.controller;

import com.evheniy.botconstruct.model.BotUser;
import com.evheniy.botconstruct.model.ChatMessage;
import com.evheniy.botconstruct.model.BotsData;
import com.evheniy.botconstruct.model.Message;
import com.evheniy.botconstruct.repository.BotUserRepository;
import com.evheniy.botconstruct.repository.BotsDataRepository;
import com.evheniy.botconstruct.repository.MessageRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/")
public class GreetingController {
    @Autowired
    BotsDataRepository botsDataRepository;



    @MessageMapping("/chat") // Відповідність з точкою входу на фронтенді
    @SendTo("/topic/messages") // Відправка повідомлень на топік /topic/messages
    public void handleChatMessage(ChatMessage message) {

        String chatId = HtmlUtils.htmlEscape(message.getSender());
        String messageText = HtmlUtils.htmlEscape(message.getContent());
        String botId = HtmlUtils.htmlEscape(message.getBotId());

        log.error("chatId:=" + chatId + " message:+" + messageText + " botId:=" + botId);
        sendMessageToUser(messageText, botId, chatId);


    }

    public void sendMessageToUser(String messageText, String botId, String chatId) {
        Optional<BotsData> byId = botsDataRepository.findById(Long.valueOf(botId));
        if (byId.isPresent()) {


            TelegramBot bot = new TelegramBot(byId.get().getToken());


            SendMessage message = new SendMessage(chatId, messageText);
            bot.execute(message);
        }
    }

}