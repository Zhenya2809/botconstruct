package com.evheniy.botconstruct.chosebottype;

import com.evheniy.botconstruct.AppBot;
import com.evheniy.botconstruct.botshandler.impl.ViberBaseUpdateHandler;
import com.evheniy.botconstruct.model.BotsData;
import com.viber.bot.api.ViberBot;
import com.viber.bot.profile.BotProfile;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Data
@Component
public class ViberBotStrategy implements BotStrategy {
    @Override
    public void setupBot(BotsData botsData, AppBot appBot) {

        ViberBot viberBot = new ViberBot(new BotProfile(botsData.getBotName(), botsData.getBotAvatarUrl()), botsData.getToken());
        ViberBaseUpdateHandler viberUpdateHandler = new ViberBaseUpdateHandler(botsData.getToken(), appBot);
        viberBot.onMessageReceived(viberUpdateHandler);
        viberBot.setWebhook(botsData.getWebhookUrl());

        log.info("viber bot started");
    }
}
