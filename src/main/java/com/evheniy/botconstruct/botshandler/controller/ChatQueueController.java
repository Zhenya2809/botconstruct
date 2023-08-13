package com.evheniy.botconstruct.botshandler.controller;

import com.evheniy.botconstruct.botshandler.mapper.ConfigMapper;
import com.evheniy.botconstruct.model.BotUser;
import com.evheniy.botconstruct.model.BotsData;
import com.evheniy.botconstruct.model.ChatQueue;
import com.evheniy.botconstruct.model.Message;
import com.evheniy.botconstruct.model.dto.MessageDto;
import com.evheniy.botconstruct.repository.BotUserRepository;
import com.evheniy.botconstruct.repository.BotsDataRepository;
import com.evheniy.botconstruct.repository.ChatQueueRepository;
import com.evheniy.botconstruct.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/functional")
public class ChatQueueController {
    @Autowired
    ChatQueueRepository chatQueueRepository;
    @Autowired
    BotUserRepository botUserRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    BotsDataRepository botsDataRepository;

    @GetMapping("/active-queues")
    public List<ChatQueue> getActiveChatQueues() {
        return chatQueueRepository.findByActiveTrue();
    }

    @PostMapping("/deactive-queues")
    public ChatQueue deActiveChatQueues(@RequestBody ChatQueueRequest request) {
        // Знаходження черги чату за ID
        ChatQueue chatQueue = chatQueueRepository.findById(request.getId()).orElse(null);
        if (chatQueue != null) {
            // Зміна стану active на false
            chatQueue.setActive(false);
            // Збереження змін у базі даних
            chatQueueRepository.save(chatQueue);
        }
        return chatQueue;
    }

    public static class ChatQueueRequest {
        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    @GetMapping("/messages/{chatId}/{botId}")
    public List<MessageDto> getMessagesByChatIdAndBotId(@PathVariable String chatId, @PathVariable String botId) {
        List<MessageDto> messageDtos = new ArrayList<>();

        // Знаходимо BotUser за chatId
        Optional<BotUser> botUserOptional = botUserRepository.findByChatId(Long.parseLong(chatId));
        if (botUserOptional.isEmpty()) {
            return messageDtos; // повертаємо порожній список, якщо BotUser не знайдений
        }
        BotUser botUser = botUserOptional.get();

        // Знаходимо BotsData за botId
        Optional<BotsData> botsDataOptional = botsDataRepository.findById(Long.parseLong(botId));
        if (botsDataOptional.isEmpty()) {
            return messageDtos; // повертаємо порожній список, якщо BotsData не знайдений
        }
        BotsData botsData = botsDataOptional.get();

        // Знаходимо повідомлення, які відповідають знайденому BotUser та BotsData
        List<Message> messages = messageRepository.findByBotUserAndBotsData(botUser, botsData);

        // Конвертуємо знайдені повідомлення в DTO
        messageDtos = ConfigMapper.INSTANCE.toMessageDto(messages);

        return messageDtos;
    }
}