package com.evheniy.botconstruct.chosebottype;

import com.evheniy.botconstruct.model.BotType;

public class BotStrategyFactory {
    public static BotStrategy create(BotType botType) {
        return switch (botType) {
            case TELEGRAM -> new TelegramBotStrategy();
            case VIBER -> new ViberBotStrategy();
            case WHATSAPP -> new WhatsAppBotStrategy();
        };
    }
}