package com.evheniy.botconstruct.botshandler;

import com.pengrad.telegrambot.model.Update;

import java.util.List;

public interface BaseUpdateHandler {
    void processUpdates(List<Update> updates);

//    void processUpdates(MyMessage myMessage);
}
