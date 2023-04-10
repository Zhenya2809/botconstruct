package com.evheniy.botconstruct.commands;


import com.evheniy.botconstruct.MyUpdatesListener;
import com.evheniy.botconstruct.model.User;

public interface Command {
    void doCommand(MyUpdatesListener myUpdatesListener);
    boolean shouldRunOnText(String text);
    User getGlobalState();
}
