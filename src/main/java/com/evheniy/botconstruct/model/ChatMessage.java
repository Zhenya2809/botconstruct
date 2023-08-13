package com.evheniy.botconstruct.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    private String sender;
    private String content;
    private String botId;

    public ChatMessage(String sender, String content, String botId) {
        this.sender = sender;
        this.content = content;
        this.botId = botId;
    }
}