package com.evheniy.botconstruct.model.dto;

import com.evheniy.botconstruct.model.BotType;
import com.evheniy.botconstruct.model.BotsData;
import com.evheniy.botconstruct.model.Message;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BotsDataDto {

    private Long id;
    private String botName;
    private BotType botType;
    private String token;
    private String webhookUrl;
    private String botAvatarUrl;
    private Long userId;

    public static BotsDataDto fromBotsData(BotsData botsData) {
        BotsDataDto botsDataDto = new BotsDataDto();
        botsDataDto.setId(botsData.getId());
        botsDataDto.setBotName(botsData.getBotName());
        botsDataDto.setBotType(botsData.getBotType());
        botsDataDto.setToken(botsData.getToken());
        botsDataDto.setWebhookUrl(botsData.getWebhookUrl());
        botsDataDto.setBotAvatarUrl(botsData.getBotAvatarUrl());
        botsDataDto.setUserId(botsData.getUser().getId());
        return botsDataDto;
    }
    public static List<BotsDataDto> fromBotsDataList(List<BotsData> botsDataList) {
        List<BotsDataDto> botsDataDtoList = new ArrayList<>();
        for (BotsData botsData : botsDataList) {
            botsDataDtoList.add(fromBotsData(botsData));
        }
        return botsDataDtoList;
    }
}
