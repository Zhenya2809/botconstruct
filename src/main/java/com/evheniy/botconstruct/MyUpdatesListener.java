package com.evheniy.botconstruct;

import com.evheniy.botconstruct.botshandler.impl.TelegramUpdateHandler;
import com.evheniy.botconstruct.botshandler.UpdateHandler;
import com.evheniy.botconstruct.model.TBots;
import com.evheniy.botconstruct.repository.CommandRepository;
import com.evheniy.botconstruct.repository.MessageRepository;
import com.evheniy.botconstruct.repository.UserRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.List;

@Slf4j
@Data
@NoArgsConstructor
public class MyUpdatesListener implements UpdatesListener {

    private UpdateHandler updateHandler;

    public MyUpdatesListener(UpdateHandler updateHandler) {
        this.updateHandler = updateHandler;
    }

    private UserRepository userRepository;
    private MessageRepository messageRepository;

    private CommandRepository commandRepository;
    private TelegramBot bot;
    private TBots tBots;
    private TelegramUpdateHandler telegramUpdateHandler;


    @Override
    public int process(List<Update> updates) {
        try {

            if (updateHandler != null) {
                updateHandler.processUpdates(updates);
            } else {
                System.err.println("UpdateHandler не ініціалізований");
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        } catch (Exception e) {
            log.error("error", e);
            e.printStackTrace();
            return UpdatesListener.CONFIRMED_UPDATES_NONE;
        } finally {
            MDC.clear();
        }
    }
}

//
//
//       try {
//               for (Update update : updates) {
//               if (update.message() != null && update.message().text() != null) {
//               String messageText = update.message().text();
//               long chatId = update.message().chat().id();
//
//               Optional<User> byChatId = userRepository.findByChatId(chatId);
//        if (byChatId.isEmpty()) {
//
//        User user = new User();
//        user.setChatId(chatId);
//        user.setFirstName(update.message().from().firstName());
//        user.setLastName(update.message().from().lastName());
//        userRepository.save(user);
//
//        Message message = new Message();
//        message.setContent(messageText);
//        message.setUser(user);
//        message.setToken(telegramBots.getToken());
//        messageRepository.save(message);
//        } else {
//        User user = byChatId.get();
//        Message message = new Message();
//        message.setContent(messageText);
//        message.setToken(telegramBots.getToken());
//        message.setUser(user);
//        messageRepository.save(message);
//        }
//
//        if (messageText.startsWith("/")) {
//        List<CommandHandler> commandHandlers = new ArrayList<>();
//        List<Command> all = commandRepository.findAll();
//        for (Command command : all) {
//        String commandText = command.getCommandText();
//        String replyText = command.getReplyText();
//
//        if (replyText.contains("{{username}}")) {
//        String replace = replyText.replace("{{username}}", update.message().from().firstName()+" "+update.message().from().lastName());
//        commandHandlers.add(new ReplyCommandHandler(commandText, replace));
//        } else {
//        commandHandlers.add(new ReplyCommandHandler(commandText, replyText));
//        }
//        }
//        String[] parts = messageText.split("\\s+");
//        String command = parts[0];
//
//
//        for (CommandHandler handler : commandHandlers) {
//        if (handler.canHandle(command)) {
//
//        handler.handle(bot, update);
//        break;
//        }
//        }
//        }
//        // ... (Other message handling logic)
//        return UpdatesListener.CONFIRMED_UPDATES_ALL;
//        }
//        }
//        } catch (Exception e) {
//        log.error("error", e);
//        e.printStackTrace();
//        } finally {
//        MDC.clear();
//        }
//
//        return UpdatesListener.CONFIRMED_UPDATES_NONE;