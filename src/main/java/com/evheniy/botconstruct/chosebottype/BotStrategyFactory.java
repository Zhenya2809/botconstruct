package com.evheniy.botconstruct.chosebottype;

import com.evheniy.botconstruct.model.BotType;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class BotStrategyFactory {
    private final SimpMessagingTemplate messagingTemplate; // Додайте поле SimpMessagingTemplate

    public BotStrategyFactory(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    public  BotStrategy create(BotType botType) {
        return switch (botType) {
            case TELEGRAM -> new TelegramBotStrategy(messagingTemplate);
            case VIBER -> new ViberBotStrategy();
            case WHATSAPP -> new WhatsAppBotStrategy();
        };
    }
}