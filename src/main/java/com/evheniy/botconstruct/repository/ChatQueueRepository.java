package com.evheniy.botconstruct.repository;

import com.evheniy.botconstruct.model.ChatQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatQueueRepository extends JpaRepository<ChatQueue,Long> {
     List<ChatQueue> findByActiveTrue();
}
