package com.evheniy.botconstruct.repository;

import com.evheniy.botconstruct.model.Command;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {
    List<Command> findByBotsData_Id(Long botId);
}
