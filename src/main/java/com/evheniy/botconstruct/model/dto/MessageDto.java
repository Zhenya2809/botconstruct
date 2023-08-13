package com.evheniy.botconstruct.model.dto;

import com.evheniy.botconstruct.model.BotUser;
import com.evheniy.botconstruct.model.BotsData;
import com.evheniy.botconstruct.model.Message;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageDto {
    private String content;
    private Long botsData;
    private Long botUser;

    public static MessageDto fromMessage(Message message) {
        MessageDto messageDto = new MessageDto();

        messageDto.setContent(message.getContent());
        messageDto.setBotsData(message.getBotsData().getId());
        messageDto.setBotUser(message.getBotUser().getChatId());
        return messageDto;
    }
}
