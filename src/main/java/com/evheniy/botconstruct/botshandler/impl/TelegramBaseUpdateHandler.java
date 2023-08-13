package com.evheniy.botconstruct.botshandler.impl;

import com.evheniy.botconstruct.ExecutionContext;
import com.evheniy.botconstruct.Service.impl.TelegramReplyService;
import com.evheniy.botconstruct.botshandler.BaseUpdateHandler;
import com.evheniy.botconstruct.model.ChatMessage;
import com.evheniy.botconstruct.commands.*;
import com.evheniy.botconstruct.model.*;
import com.evheniy.botconstruct.repository.*;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class TelegramBaseUpdateHandler implements BaseUpdateHandler {

    private final SimpMessagingTemplate messagingTemplate;

    private BotUserRepository botUserRepository;

    private MessageRepository messageRepository;

    private CommandRepository commandRepository;

    private BotsDataRepository botsDataRepository;

    private ChatQueueRepository chatQueueRepository;

    private BotsData botsData;

    private TelegramBot bot;

    @Override
    public void processUpdates(List<Update> updates) {
        for (Update update : updates) {
            if (update.message() != null && update.message().text() != null) {
                String messageText = update.message().text();
                long chatId = update.message().chat().id();
                Long botId = getBotIdByUpdate();
                BotUser botUser = botUserRepository.findByChatId(chatId).orElseGet(() -> {
                    BotUser newUser = new BotUser();
                    newUser.setChatId(chatId);
                    newUser.setFirstName(update.message().from().firstName());
                    newUser.setLastName(update.message().from().lastName());
                    botUserRepository.save(newUser);
                    return newUser;
                });

                Message message = new Message();
                message.setContent(messageText);
                message.setBotUser(botUser);
                message.setBotsData(botsData);
                messageRepository.save(message);

                sendChatMessageToWebSocket(botUser.getChatId().toString(), messageText, botsData.getId().toString());
                processCommands(update, botId);
            }
        }
    }

    private Long getBotIdByUpdate() {
        return botsData.getId();
    }

    private void sendChatMessageToWebSocket(String sender, String content, String botId) {
        ChatMessage chatMessage = new ChatMessage(sender, content, botId);
        messagingTemplate.convertAndSend("/topic/messages", chatMessage);
    }

    private void processCommands(Update update, Long botId) {
        String messageText = update.message().text();
        long chatId = update.message().chat().id();

        if (messageText.startsWith("/")) {
            List<CommandHandler> commandHandlerList = new ArrayList<>();
            List<Command> allCommands = commandRepository.findByBotsData_Id(botId);

            for (Command command : allCommands) {
                String commandText = command.getCommandText();
                String replyText = command.getReplyText();
                String typeCommand = command.getTypeCommand();
                String json = command.getJson();

                CommandHandler handler = createHandler(typeCommand, commandText, replyText, json, botId,chatId);
                commandHandlerList.add(handler);
            }

            String[] parts = messageText.split("\\s+");
            String command = parts[0];

            ExecutionContext context = new ExecutionContext(new TelegramReplyService(bot, chatId));
            for (CommandHandler handler : commandHandlerList) {
                if (handler.canHandle(command)) {
                    handler.handle(context);
                    break;
                }
            }
        }
    }

    private CommandHandler createHandler(String typeCommand, String commandText, String replyText, String json, Long botId, Long chatId) {
        if (typeCommand == null) {
            return new ReplyCommandHandler(commandText, replyText);
        }

        return switch (typeCommand) {
            case "socialCommand" -> new SocialCommandHandler(commandText, replyText, json);
            case "functionalCommand" -> new FunctionCommandHandler(commandText, json, botId, chatId,chatQueueRepository);
            default -> new ReplyCommandHandler(commandText, replyText);
        };
    }

    private String replaceUsernamePlaceholder(String replyText, String firstName, String lastName) {
        return replyText.replace("{{username}}", firstName + " " + lastName);
    }
}
