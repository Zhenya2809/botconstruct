package com.evheniy.botconstruct;

import com.evheniy.botconstruct.botshandler.impl.TelegramBaseUpdateHandler;
import com.evheniy.botconstruct.chosebottype.BotStrategy;
import com.evheniy.botconstruct.chosebottype.BotStrategyFactory;
import com.evheniy.botconstruct.model.BotType;
import com.evheniy.botconstruct.model.Command;
import com.evheniy.botconstruct.model.ConfigurationBot;
import com.evheniy.botconstruct.model.BotsData;
import com.evheniy.botconstruct.repository.*;
import com.pengrad.telegrambot.TelegramBot;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Data
@Component
@Slf4j
public class AppBot {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AllBotsRepository allBotsRepository;
    @Autowired
    private ConfigurationBotRepository configurationBotRepository;
    @Autowired
    private CommandRepository commandRepository;


    private TelegramBot bot;


    public void addNewBot() {
        String tokenId = "6125153463:AAEq0IIJNvufXmitJfgiwq69V6HrXnd8ifc";
//        token.setToken("5268155371:AAG3RgrkWWJoVAprsablbLDSUQRkydn2Ftc");
        Optional<BotsData> tokenByToken = allBotsRepository.findTokenByToken(tokenId);
        if (tokenByToken.isEmpty()) {
            BotsData telegramBot = new BotsData();

            telegramBot.setBotType(BotType.TELEGRAM);

            telegramBot.setToken(tokenId);

            Command telegramCommands = new Command();
            telegramCommands.setCommandText("/help");
            telegramCommands.setReplyText("чим можу допомгти?");
            Set<Command> commandSet = new HashSet<>();
            telegramCommands.setBotsData(telegramBot);
            commandSet.add(telegramCommands);

            telegramBot.setCommands(commandSet);


            //viber
            BotsData viberBot = new BotsData();
            Command viberCommands = new Command();
            viberCommands.setCommandText("/help");
            viberCommands.setReplyText("чим можу допомгти?");
            Set<Command> commanViberdSet = new HashSet<>();
            telegramCommands.setBotsData(viberBot);
            commandSet.add(viberCommands);
            viberBot.setCommands(commanViberdSet);

            viberBot.setBotType(BotType.VIBER);
            viberBot.setBotName("Pomoika");
            viberBot.setToken("50e8ff9f0867df82-2ca514cfbc3112a5-e420114b9153a093");
            viberBot.setBotAvatarUrl("https://cdn.pixabay.com/photo/2018/11/13/21/43/avatar-3814049_960_720.png");
            viberBot.setWebhookUrl("https://cdb3-193-109-8-198.eu.ngrok.io");

            ConfigurationBot viberConfigurationBot = new ConfigurationBot();
            viberConfigurationBot.setCodeFromBD("code from db");
            viberConfigurationBot.setLatitude(0.4141f);
            viberConfigurationBot.setLongitude(0.1412f);
            viberConfigurationBot.setHelpMessage("help message1");
            viberConfigurationBot.setGreetingMessage("hello message");
            viberConfigurationBot.setImageURL("this is image url");
            viberConfigurationBot.setBotsData(viberBot);

            telegramBot.setConfigurationBot(viberConfigurationBot);

            viberBot.setConfigurationBot(viberConfigurationBot);
            allBotsRepository.save(viberBot);
            //viber


            ConfigurationBot telegramConfigurationBot = new ConfigurationBot();
            telegramConfigurationBot.setCodeFromBD("code from db");
            telegramConfigurationBot.setLatitude(0.4141f);
            telegramConfigurationBot.setLongitude(0.1412f);
            telegramConfigurationBot.setHelpMessage("help message1");
            telegramConfigurationBot.setGreetingMessage("hello message");
            telegramConfigurationBot.setImageURL("this is image url");
            telegramConfigurationBot.setBotsData(telegramBot);

            telegramBot.setConfigurationBot(telegramConfigurationBot);

            allBotsRepository.save(telegramBot);


        }
    }

    @PostConstruct
    public void init() {
        addNewBot();
        List<BotsData> all = allBotsRepository.findAll();
        System.out.println(all);
        for (BotsData botsData : all) {
            ConfigurationBot configBot = configurationBotRepository.findByBotsData(botsData);
            if (configBot != null) {
                BotStrategy botStrategy = BotStrategyFactory.create(botsData.getBotType());
                botStrategy.setupBot(botsData, this);
            }
        }
    }
}
