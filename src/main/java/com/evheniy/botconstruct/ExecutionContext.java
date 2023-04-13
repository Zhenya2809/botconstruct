package com.evheniy.botconstruct;

import com.pengrad.telegrambot.model.Update;

import lombok.Data;

@Data
public class ExecutionContext {
    private Bot bot;
    private Long chatId;
    private String firstName;
    private String lastName;
    private String inputText;
    private Update update;


}
