package com.evheniy.botconstruct.Service.impl;

import com.evheniy.botconstruct.model.*;
import com.evheniy.botconstruct.repository.BotsDataRepository;
import com.evheniy.botconstruct.repository.ChatQueueRepository;
import com.evheniy.botconstruct.repository.UserRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BotsDataService {
    @Autowired
    private BotsDataRepository botsDataRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatQueueRepository chatQueueRepository;

    public CreateBotResponse createBot(CreateBotRequest createBotRequest) {
        try {
            Optional<BotsData> byToken = botsDataRepository.findByToken(createBotRequest.getBotToken());
            if (byToken.isPresent()) {
                return new CreateBotResponse("ERROR", "бот з таким токеном вже існує");
            } else {
                BotsData bot = new BotsData();
                bot.setToken(createBotRequest.getBotToken());
                bot.setBotName(createBotRequest.getBotName());
                bot.setWebhookUrl(createBotRequest.getWebhookURL());
                BotType botType = BotType.valueOf(createBotRequest.getTypeBot().toUpperCase());
                bot.setBotType(botType);
                String name = SecurityContextHolder.getContext().getAuthentication().getName();
                Optional<User> userByPhone = userRepository.findUserByPhone(name);
                userByPhone.ifPresent(bot::setUser);
                log.info("user set " + userByPhone.get().getPhone());
                botsDataRepository.save(bot);
                return new CreateBotResponse("SUCCESS", createBotRequest.getBotName()); // Повертаємо відповідь з успішним статусом та ідентифікатором бота
            }
        } catch (Exception e) {
            return new CreateBotResponse("ERROR", e.getMessage()); // Повертаємо відповідь з помилкою
        }

    }

    public List<ChatQueue> getActiveChatQueues() {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userByPhone = userRepository.findUserByPhone(name);
        if (userByPhone.isPresent()) {
            User user = userByPhone.get();
            return chatQueueRepository.findByActiveTrueAndUserId(user.getId());
        }

        return Collections.emptyList();
    }

    public void saveChatQueue(Long chatId, Long botId) {
        ChatQueue chatQueue = new ChatQueue();
        chatQueue.setChatId(chatId);
        chatQueue.setBotId(botId);
        chatQueue.setActive(true);
        Optional<BotsData> byId = botsDataRepository.findById(botId);
        byId.ifPresent(botsData -> chatQueue.setUserId(botsData.getUser().getId()));
        chatQueueRepository.save(chatQueue);
    }
    public void sendMessageToUser(String messageText, String botId, String chatId) {
        Optional<BotsData> byId = botsDataRepository.findById(Long.valueOf(botId));
        if (byId.isPresent()) {
            TelegramBot bot = new TelegramBot(byId.get().getToken());
            SendMessage message = new SendMessage(chatId, messageText);
            bot.execute(message);
        }
    }
}
