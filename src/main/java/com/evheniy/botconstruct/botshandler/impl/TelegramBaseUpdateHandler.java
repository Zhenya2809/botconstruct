package com.evheniy.botconstruct.botshandler.impl;

import com.evheniy.botconstruct.ExecutionContext;
import com.evheniy.botconstruct.Service.impl.TelegramReplyService;
import com.evheniy.botconstruct.botshandler.BaseUpdateHandler;
import com.evheniy.botconstruct.commands.CommandHandler;
import com.evheniy.botconstruct.commands.ReplyCommandHandler;
import com.evheniy.botconstruct.model.Command;
import com.evheniy.botconstruct.model.Message;
import com.evheniy.botconstruct.model.BotsData;
import com.evheniy.botconstruct.model.User;
import com.evheniy.botconstruct.repository.*;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Slf4j
public class TelegramBaseUpdateHandler implements BaseUpdateHandler {


    private UserRepository userRepository;

    private MessageRepository messageRepository;

    private CommandRepository commandRepository;

    private BotsData botsData;

    private TelegramBot bot;


    @Override
    public void processUpdates(List<Update> updates) {

        for (Update update : updates) {
            if (update.message() != null && update.message().text() != null) {
                String messageText = update.message().text();
                long chatId = update.message().chat().id();

                Optional<User> byChatId = userRepository.findByChatId(chatId);
                if (byChatId.isEmpty()) {

                    User user = new User();
                    user.setChatId(chatId);
                    user.setFirstName(update.message().from().firstName());
                    user.setLastName(update.message().from().lastName());
                    userRepository.save(user);

                    Message message = new Message();
                    message.setContent(messageText);
                    message.setUser(user);
                    message.setToken(botsData.getToken());
                    messageRepository.save(message);
                } else {
                    User user = byChatId.get();
                    Message message = new Message();
                    message.setContent(messageText);
                    message.setToken(botsData.getToken());
                    message.setUser(user);
                    messageRepository.save(message);
                }

                if (messageText.startsWith("/")) {
                    List<CommandHandler> commandHandlers = new ArrayList<>();
                    List<Command> all = commandRepository.findAll();
                    for (Command command : all) {
                        String commandText = command.getCommandText();
                        String replyText = command.getReplyText();

                        if (replyText.contains("{{username}}")) {
                            replyText = replyText.replace("{{username}}", update.message().from().firstName() + " " + update.message().from().lastName());
                        }
                        commandHandlers.add(new ReplyCommandHandler(commandText, replyText));
                    }
                    String[] parts = messageText.split("\\s+");
                    String command = parts[0];

                    ExecutionContext context = new ExecutionContext(new TelegramReplyService(bot, chatId));
                    for (CommandHandler handler : commandHandlers) {
                        if (handler.canHandle(command)) {

                            handler.handle(context);
                            break;
                        }
                    }
                }
            }
        }
    }
}
