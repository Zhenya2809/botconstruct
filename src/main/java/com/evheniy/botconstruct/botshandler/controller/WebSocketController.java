package com.evheniy.botconstruct.botshandler.controller;

import com.evheniy.botconstruct.Service.impl.BotsDataService;
import com.evheniy.botconstruct.model.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

@Slf4j
@RestController
@RequestMapping("/")
public class WebSocketController {
    @Autowired
    BotsDataService botsDataService;

    @MessageMapping("/chat") // Відповідність з точкою входу на фронтенді
    @SendTo("/topic/messages") // Відправка повідомлень на топік /topic/messages
    public void handleChatMessage(ChatMessage message) {

        String chatId = HtmlUtils.htmlEscape(message.getSender());
        String messageText = HtmlUtils.htmlEscape(message.getContent());
        String botId = HtmlUtils.htmlEscape(message.getBotId());
        botsDataService.sendMessageToUser(messageText, botId, chatId);


    }


}