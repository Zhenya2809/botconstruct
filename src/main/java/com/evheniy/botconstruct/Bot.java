package com.evheniy.botconstruct;

import com.evheniy.botconstruct.commands.CommandHandler;
import com.evheniy.botconstruct.model.ConfigurationBot;
import com.evheniy.botconstruct.model.Token;
import com.evheniy.botconstruct.repository.ConfigurationBotRepository;
import com.evheniy.botconstruct.repository.TokenRepository;
import com.pengrad.telegrambot.TelegramBot;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Data
@Component
@Slf4j
public class Bot {
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private ConfigurationBotRepository configurationBotRepository;

    @Autowired
    public List<CommandHandler> commands;


    private TelegramBot bot;

    @PostConstruct
    public void init() {
        List<Token> all = tokenRepository.findAll();
        System.out.println(all);
        for (Token token : all) {
            ConfigurationBot configBot = configurationBotRepository.findByToken(token);
            if (configBot != null) {
                token.setConfigurationBot(configBot);
                bot = BotConstructor.createBot(token.getToken(), null);
                MyUpdatesListener updatesListener = new MyUpdatesListener (bot, token, commands);
                bot.setUpdatesListener(updatesListener);
            }

        }
    }
}