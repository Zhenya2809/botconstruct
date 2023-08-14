package com.evheniy.botconstruct.Service.impl;

import com.evheniy.botconstruct.botshandler.exception.ResourceNotFoundException;
import com.evheniy.botconstruct.botshandler.mapper.ConfigMapper;
import com.evheniy.botconstruct.model.BotUser;
import com.evheniy.botconstruct.model.BotsData;
import com.evheniy.botconstruct.model.Message;
import com.evheniy.botconstruct.model.UserResponse;
import com.evheniy.botconstruct.model.dto.MessageDto;
import com.evheniy.botconstruct.repository.BotUserRepository;
import com.evheniy.botconstruct.repository.BotsDataRepository;
import com.evheniy.botconstruct.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BotUserService {
    @Autowired
    BotUserRepository botUserRepository;
    @Autowired
    BotsDataRepository botsDataRepository;
    @Autowired
    MessageRepository messageRepository;

    public UserResponse findUserByChatId(Long chatId) {
        BotUser botUser = botUserRepository.findByChatId(chatId)
                .orElseThrow(() -> new ResourceNotFoundException("BotUser not found with id " + chatId));
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(botUser.getFirstName() + " " + botUser.getLastName());
        return userResponse;
    }
    public List<MessageDto> getMessagesByChatIdAndBotId(String chatId, String botId){
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
