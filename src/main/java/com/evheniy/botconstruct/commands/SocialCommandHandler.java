package com.evheniy.botconstruct.commands;

import com.evheniy.botconstruct.ExecutionContext;

public class SocialCommandHandler implements CommandHandler {

    private final String command;
    private final String reply;
    private final String json;

    public SocialCommandHandler(String command, String reply, String json) {
        this.command = command;
        this.reply = reply;
        this.json = json;
    }

    @Override
    public boolean canHandle(String command) {
        return this.command.equalsIgnoreCase(command);
    }

    @Override
    public void handle(ExecutionContext context) {
        // Використовуйте JSON-інформацію для вибору та відправлення картинок
    }

    @Override
    public String typeCommand() {
        return "socialCommand";
    }
}
