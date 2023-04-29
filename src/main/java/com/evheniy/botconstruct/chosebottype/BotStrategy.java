package com.evheniy.botconstruct.chosebottype;

import com.evheniy.botconstruct.AppBot;
import com.evheniy.botconstruct.model.BotsData;

public interface BotStrategy {
    void setupBot(BotsData botsData, AppBot appBot);
}