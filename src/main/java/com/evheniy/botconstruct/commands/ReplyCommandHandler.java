package com.evheniy.botconstruct.commands;

import com.evheniy.botconstruct.ExecutionContext;


public class ReplyCommandHandler implements CommandHandler {

    private final String command;
    private final String replyMessage;



    public ReplyCommandHandler(String command, String replyMessage) {
        this.command = command;
        this.replyMessage = replyMessage;

    }

    @Override
    public boolean canHandle(String command) {
        return this.command.equalsIgnoreCase(command);
    }

    @Override
    public void handle(ExecutionContext context) {
        context.getReplyService().reply(replyMessage);
    }

    @Override
    public String typeCommand() {
        return "replyMessage";
    }

}