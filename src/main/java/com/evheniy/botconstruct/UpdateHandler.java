package com.evheniy.botconstruct;

import com.pengrad.telegrambot.model.Update;

import java.util.List;

public interface UpdateHandler {
    void processUpdates(List<Update> updates);

//    void processUpdates(MyMessage myMessage);
}
