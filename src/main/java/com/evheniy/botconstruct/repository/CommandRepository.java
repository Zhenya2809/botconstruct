package com.evheniy.botconstruct.repository;

import com.evheniy.botconstruct.model.Command;
import com.evheniy.botconstruct.model.TBots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {

}
