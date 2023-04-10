package com.evheniy.botconstruct;


import com.evheniy.botconstruct.model.User;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendLocation;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.HashMap;
import java.util.List;

@Slf4j

public class MyUpdatesListener implements UpdatesListener {
    private final TelegramBot bot;
    public HashMap<Long, User> user = new HashMap<>();
    public MyUpdatesListener(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public int process(List<Update> updates) {
        try {
            for (Update update : updates) {
                if (update.message() != null && update.message().text() != null) {
                    Long chatId = update.message().chat().id();
                    String firstName = update.message().chat().firstName();
                    String lastName = update.message().chat().lastName();
                    String inputText = update.message().text();
                    MDC.put("firstName", firstName);
                    MDC.put("lastName", lastName);

                    user.computeIfAbsent(chatId, a -> new User(chatId, firstName, lastName));

                }
            }
        } catch (Exception e) {
            log.error("error", e);
            e.printStackTrace();

        } finally {
            MDC.clear();
        }
//        for (Update update : updates) {
//            if (update.message() != null && update.message().text() != null) {
//                long chatId = update.message().chat().id();
//                String messageText = update.message().text();
//
//                if (messageText.startsWith("/")) {
//                    if ("/help".equalsIgnoreCase(messageText)) {
//                        sendHelp(chatId, """
//                                Ось список доступних команд:
//                                /help - отримати список доступних команд
//                                """);
//                    } else {
//                        String unknownCommandMessage = "Вибач хуїла, я не розумію цю команду. Спробуйте використати /help, щоб побачити доступні команди.";
//                        bot.execute(new SendMessage(chatId, unknownCommandMessage));
//                    }
//                } else {
//
//                    sendGreeting(
//                            chatId,
//                            "Привіт! Я твій телеграм-бот, як я можу тобі допомогти?"
//                    );
//                }
//            }
//            return UpdatesListener.CONFIRMED_UPDATES_ALL;
//
//        }
        return UpdatesListener.CONFIRMED_UPDATES_NONE;
    }

    private void sendGreeting(long chatId, String greetingMessage) {

        bot.execute(new SendMessage(chatId, greetingMessage));
    }

    private void sendMessage(long chatId, String message) {
        bot.execute(new SendMessage(chatId, message));
    }

    private void sendImage(long chatId, String imageURL) {
        SendPhoto sendPhoto = new SendPhoto(chatId, imageURL);
        bot.execute(sendPhoto);
    }

    public void sendAddress(long chatId, float longitude, float latitude) {
        SendLocation sendLocation = new SendLocation(chatId, latitude, longitude);
        bot.execute(sendLocation);
    }

    private void sendHelp(long chatId, String commands) {
        bot.execute(new SendMessage(chatId, commands).parseMode(ParseMode.Markdown));
    }
}