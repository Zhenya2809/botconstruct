package com.evheniy.botconstruct;

import com.evheniy.botconstruct.Service.impl.BotsDataService;
import com.evheniy.botconstruct.botshandler.impl.TelegramBaseUpdateHandler;
import com.evheniy.botconstruct.botshandler.BaseUpdateHandler;
import com.evheniy.botconstruct.model.BotsData;
import com.evheniy.botconstruct.repository.*;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.List;

@Slf4j
@Data
@NoArgsConstructor
public class MyUpdatesListener implements UpdatesListener {

    public MyUpdatesListener(BaseUpdateHandler baseUpdateHandler) {
        this.baseUpdateHandler = baseUpdateHandler;
    }

    private BaseUpdateHandler baseUpdateHandler;

    private BotUserRepository botUserRepository;
    private MessageRepository messageRepository;
    private ChatQueueRepository chatQueueRepository;
    private UserRepository userRepository;
    private BotsDataService botsDataService;

    private CommandRepository commandRepository;
    private TelegramBot bot;
    private BotsData botsData;

    private TelegramBaseUpdateHandler telegramUpdateHandler;

    @Override
    public int process(List<Update> updates) {
        try {

            if (baseUpdateHandler != null) {
                baseUpdateHandler.processUpdates(updates);
            } else {
                System.err.println("UpdateHandler не ініціалізований");
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        } catch (Exception e) {
            log.error("error", e);
            e.printStackTrace();
            return UpdatesListener.CONFIRMED_UPDATES_NONE;
        } finally {
            MDC.clear();
        }
    }
}