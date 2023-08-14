package com.evheniy.botconstruct.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateBotRequest {
    private String typeBot;
    private String botToken;
    private String webhookURL;
    private String botName;
}
