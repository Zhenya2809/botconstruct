package com.evheniy.botconstruct.model.dto;

import com.evheniy.botconstruct.model.BotsData;
import com.evheniy.botconstruct.model.Message;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BotsDataDto {

    private Long id;
    private String botName;

    public static BotsDataDto fromBotsData(BotsData botsData) {
        BotsDataDto botsDataDto = new BotsDataDto();
        botsDataDto.setId(botsData.getId());
        botsDataDto.setBotName(botsData.getBotName());
        return botsDataDto;
    }

}
