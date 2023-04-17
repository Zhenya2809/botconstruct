package com.evheniy.botconstruct.repository;

import com.evheniy.botconstruct.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Long> {
}
