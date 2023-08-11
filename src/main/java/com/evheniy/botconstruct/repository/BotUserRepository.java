package com.evheniy.botconstruct.repository;

import com.evheniy.botconstruct.model.BotUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BotUserRepository extends JpaRepository<BotUser, Long> {
    Optional<BotUser> findByChatId(Long chatId);
}
