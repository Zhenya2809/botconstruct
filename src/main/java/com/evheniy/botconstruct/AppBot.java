package com.evheniy.botconstruct;

import com.evheniy.botconstruct.botshandler.impl.TelegramUpdateHandler;
import com.evheniy.botconstruct.model.Command;
import com.evheniy.botconstruct.model.ConfigurationBot;
import com.evheniy.botconstruct.model.TBots;
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
    private TokenRepository tokenRepository;
    @Autowired
    private ConfigurationBotRepository configurationBotRepository;
    @Autowired
    private CommandRepository commandRepository;


    private TelegramBot bot;


    public void addNewBot() {
        String tokenId = "6125153463:AAEq0IIJNvufXmitJfgiwq69V6HrXnd8ifc";
//        token.setToken("5268155371:AAG3RgrkWWJoVAprsablbLDSUQRkydn2Ftc");
        Optional<TBots> tokenByToken = tokenRepository.findTokenByToken(tokenId);
        if (tokenByToken.isEmpty()) {
            TBots tBots = new TBots();

            tBots.setToken(tokenId);

            Command command = new Command();
            command.setCommandText("/help");
            command.setReplyText("чим можу допомгти?");
            Set<Command> commandSet = new HashSet<>();
            command.setTBots(tBots);
            commandSet.add(command);


            tBots.setCommands(commandSet);

            ConfigurationBot configurationBot = new ConfigurationBot();
            configurationBot.setCodeFromBD("code from db");
            configurationBot.setLatitude(0.4141f);
            configurationBot.setLongitude(0.1412f);
            configurationBot.setHelpMessage("help message1");
            configurationBot.setGreetingMessage("hello message");
            configurationBot.setImageURL("this is image url");
            configurationBot.setTbots(tBots);

            tBots.setConfigurationBot(configurationBot);

            tokenRepository.save(tBots);
        }
    }

    @PostConstruct
    public void init() {
        addNewBot();
        List<TBots> all = tokenRepository.findAll();
        System.out.println(all);
        for (TBots tBots : all) {
            ConfigurationBot configBot = configurationBotRepository.findByTbots(tBots);
            if (configBot != null) {

                bot = BotConstructor.createBot(tBots.getToken(), null);
                tBots.setConfigurationBot(configBot);

                TelegramUpdateHandler telegramUpdateHandler = new TelegramUpdateHandler();
                telegramUpdateHandler.setTBots(tBots);
                telegramUpdateHandler.setUserRepository(userRepository);
                telegramUpdateHandler.setMessageRepository(messageRepository);
                telegramUpdateHandler.setCommandRepository(commandRepository);
                telegramUpdateHandler.setBot(bot);

                MyUpdatesListener updatesListener = new MyUpdatesListener(telegramUpdateHandler);
                updatesListener.setBot(bot);
                updatesListener.setTBots(tBots);
                updatesListener.setCommandRepository(commandRepository);
                updatesListener.setUserRepository(userRepository);
                updatesListener.setMessageRepository(messageRepository);


                bot.setUpdatesListener(updatesListener);
            }
        }
    }
}