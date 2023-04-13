package com.evheniy.botconstruct;

import com.evheniy.botconstruct.commands.CommandHandler;
import com.evheniy.botconstruct.model.Token;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Data
@Component
@NoArgsConstructor
public class MyUpdatesListener implements UpdatesListener {
    private  TelegramBot bot;
    private  Token token;
    private  List<CommandHandler> commandHandlers;

    public MyUpdatesListener(TelegramBot bot, Token token, List<CommandHandler> commandHandlers) {
        this.bot = bot;
        this.token = token;
        this.commandHandlers = commandHandlers;
    }

    @Override
    public int process(List<Update> updates) {
        try {
            for (Update update : updates) {
                if (update.message() != null && update.message().text() != null) {
                    String messageText = update.message().text();
                    long chatId = update.message().chat().id();

                    if (messageText.startsWith("/")) {
                        String[] parts = messageText.split("\\s+");
                        String command = parts[0];

                        for (CommandHandler handler : commandHandlers) {
                            if (handler.canHandle(command)) {
                                handler.handle(bot, update);
                                break;
                            }
                        }
                    }
                    // ... (Other message handling logic)
                    return UpdatesListener.CONFIRMED_UPDATES_ALL;
                }
            }
        } catch (Exception e) {
            log.error("error", e);
            e.printStackTrace();
        } finally {
            MDC.clear();
        }

        return UpdatesListener.CONFIRMED_UPDATES_NONE;
    }
}