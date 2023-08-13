package com.evheniy.botconstruct.repository;

import com.evheniy.botconstruct.model.BotUser;
import com.evheniy.botconstruct.model.BotsData;
import com.evheniy.botconstruct.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
//    List<Message> findByBotUserAndBotsData_Id(BotUser botUser, Long botId);
    List<Message> findByBotUserAndBotsData(BotUser botUser, BotsData botsData);
    List<Message> findByBotUser(BotUser botUser);
}
