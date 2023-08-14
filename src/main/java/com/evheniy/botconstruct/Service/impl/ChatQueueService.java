package com.evheniy.botconstruct.Service.impl;

import com.evheniy.botconstruct.model.ChatQueue;
import com.evheniy.botconstruct.model.ChatQueueRequest;
import com.evheniy.botconstruct.repository.ChatQueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatQueueService {
    @Autowired
    ChatQueueRepository chatQueueRepository;

    public ChatQueue findChatMessageAndSetFalse(ChatQueueRequest request){
        ChatQueue chatQueue = chatQueueRepository.findById(request.getId()).orElse(null);
        if (chatQueue != null) {
            // Зміна стану active на false
            chatQueue.setActive(false);
            // Збереження змін у базі даних
            chatQueueRepository.save(chatQueue);
        }
        return chatQueue;
    }

}
