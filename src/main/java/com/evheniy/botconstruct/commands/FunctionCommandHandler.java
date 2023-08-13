package com.evheniy.botconstruct.commands;

import com.evheniy.botconstruct.ExecutionContext;
import com.evheniy.botconstruct.model.ChatQueue;
import com.evheniy.botconstruct.repository.ChatQueueRepository;


public class FunctionCommandHandler implements CommandHandler {

    private final String command; // Команда, яку обробляє обробник
    private final String json;
    private final Long botId;
    private final Long chatId;
    private final ChatQueueRepository chatQueueRepository; // Репозиторій для взаємодії з таблицею черги

    // Конструктор, який приймає команду
    public FunctionCommandHandler(String command, String json, Long botId, Long chatId, ChatQueueRepository chatQueueRepository) {
        this.command = command;
        this.json = json;
        this.botId = botId;
        this.chatId = chatId;
        this.chatQueueRepository = chatQueueRepository;
    }

    // Перевірка, чи обробник може обробити дану команду
    @Override
    public boolean canHandle(String command) {
        return this.command.equalsIgnoreCase(command);
    }

    // Обробка функціональної команди
    @Override
    public void handle(ExecutionContext context) {
        // Створення нового об'єкта ChatQueue
        ChatQueue chatQueue = new ChatQueue();
        chatQueue.setChatId(chatId);
        chatQueue.setBotId(botId);
        chatQueue.setActive(true);

        // Збереження об'єкта в базі даних
        chatQueueRepository.save(chatQueue);
    }

    // Повернення типу команди, може бути корисним для ідентифікації обробника
    @Override
    public String typeCommand() {
        return "functionCommand";
    }
}
