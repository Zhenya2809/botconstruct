package com.evheniy.botconstruct.repository;

import com.evheniy.botconstruct.model.ConfigurationBot;
import com.evheniy.botconstruct.model.TBots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationBotRepository extends JpaRepository<ConfigurationBot,Long> {
    ConfigurationBot findByTbots(TBots tbots);
}
