package com.evheniy.botconstruct.model.dto;

import com.evheniy.botconstruct.model.BotUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BotUserDto {
    private Long chatId;
    private String firstName;
    private String lastName;

    public static BotUserDto fromBotUser(BotUser botUser) {
        BotUserDto botUserDto = new BotUserDto();
        botUserDto.setChatId(botUser.getChatId());
        botUserDto.setLastName(botUser.getLastName());
        botUserDto.setFirstName(botUser.getFirstName());
        return botUserDto;
    }
}
