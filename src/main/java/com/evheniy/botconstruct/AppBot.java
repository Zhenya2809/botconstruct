package com.evheniy.botconstruct;

import com.evheniy.botconstruct.botshandler.impl.TelegramBaseUpdateHandler;
import com.evheniy.botconstruct.model.Command;
import com.evheniy.botconstruct.model.ConfigurationBot;
import com.evheniy.botconstruct.model.AllBots;
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
        Optional<AllBots> tokenByToken = allBotsRepository.findTokenByToken(tokenId);
        if (tokenByToken.isEmpty()) {
            AllBots allBots = new AllBots();

            allBots.setBotType(BotType.TELEGRAM);

            allBots.setToken(tokenId);

            Command command = new Command();
            command.setCommandText("/help");
            command.setReplyText("чим можу допомгти?");
            Set<Command> commandSet = new HashSet<>();
            command.setAllBots(allBots);
            commandSet.add(command);


            allBots.setCommands(commandSet);

            ConfigurationBot configurationBot = new ConfigurationBot();
            configurationBot.setCodeFromBD("code from db");
            configurationBot.setLatitude(0.4141f);
            configurationBot.setLongitude(0.1412f);
            configurationBot.setHelpMessage("help message1");
            configurationBot.setGreetingMessage("hello message");
            configurationBot.setImageURL("this is image url");
            configurationBot.setAllBots(allBots);

            allBots.setConfigurationBot(configurationBot);

            allBotsRepository.save(allBots);
        }
    }

    @PostConstruct
    public void init() {
        addNewBot();
        List<AllBots> all = allBotsRepository.findAll();
        System.out.println(all);
        for (AllBots allBots : all) {
            ConfigurationBot configBot = configurationBotRepository.findByAllBots(allBots);
            if (configBot != null) {
                switch (allBots.getBotType()) {
                    case TELEGRAM -> {
                        bot = BotConstructor.createBot(allBots.getToken(), null);
                        allBots.setConfigurationBot(configBot);
                        TelegramBaseUpdateHandler telegramUpdateHandler = new TelegramBaseUpdateHandler();
                        telegramUpdateHandler.setAllBots(allBots);
                        telegramUpdateHandler.setUserRepository(userRepository);
                        telegramUpdateHandler.setMessageRepository(messageRepository);
                        telegramUpdateHandler.setCommandRepository(commandRepository);
                        telegramUpdateHandler.setBot(bot);
                        MyUpdatesListener updatesListener = new MyUpdatesListener(telegramUpdateHandler);
                        updatesListener.setBot(bot);
                        updatesListener.setAllBots(allBots);
                        updatesListener.setCommandRepository(commandRepository);
                        updatesListener.setUserRepository(userRepository);
                        updatesListener.setMessageRepository(messageRepository);
                        bot.setUpdatesListener(updatesListener);
                    }
                    case VIBER -> log.error("для вайберу ніхуя немає");
                    case WHATSAPP -> log.error("для вотсапа ніхуя немає");
                    default -> throw new IllegalArgumentException("Непідтримуваний тип бота: " + allBots.getBotType());
                }

            }
        }
    }
}