package com.evheniy.botconstruct;

import com.evheniy.botconstruct.model.Token;
import com.evheniy.botconstruct.repository.TokenRepository;
import com.pengrad.telegrambot.TelegramBot;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Data
@Component
public class Bot {
    @Autowired
    private TokenRepository tokenRepository;

    private TelegramBot bot;

    @PostConstruct
    public void init() {
        List<Token> all = tokenRepository.findAll();
        System.out.println(all);
        for (Token token : all) {

            bot = BotConstructor.createBot(token.getToken(), null);
            MyUpdatesListener updatesListener = new MyUpdatesListener(bot);
            bot.setUpdatesListener(updatesListener);

        }

    }
}