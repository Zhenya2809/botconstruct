package com.evheniy.botconstruct.botshandler.controller;

import com.evheniy.botconstruct.Service.impl.BotUserService;
import com.evheniy.botconstruct.Service.impl.BotsDataService;
import com.evheniy.botconstruct.Service.impl.ChatQueueService;
import com.evheniy.botconstruct.model.*;
import com.evheniy.botconstruct.model.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/functional")
public class ChatQueueController {

    @Autowired
    BotUserService botUserService;

    @Autowired
    BotsDataService botsDataService;

    @Autowired
    ChatQueueService chatQueueService;

    @GetMapping("/active-queues")
    public List<ChatQueue> getActiveChatQueues() {
        return botsDataService.getActiveChatQueues();
    }

    @GetMapping("/getusername/{chatId}")
    public UserResponse getUsername(@PathVariable Long chatId) {
        return botUserService.findUserByChatId(chatId);
    }

    @PostMapping("/deactive-queues")
    public ChatQueue deActiveChatQueues(@RequestBody ChatQueueRequest request) {
        return chatQueueService.findChatMessageAndSetFalse(request);
    }

    @GetMapping("/messages/{chatId}/{botId}")
    public List<MessageDto> getMessagesByChatIdAndBotId(@PathVariable String chatId, @PathVariable String botId) {
        return botUserService.getMessagesByChatIdAndBotId(chatId, botId);
    }
}