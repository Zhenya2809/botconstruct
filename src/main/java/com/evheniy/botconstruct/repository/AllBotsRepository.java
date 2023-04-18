package com.evheniy.botconstruct.repository;

import com.evheniy.botconstruct.model.AllBots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AllBotsRepository extends JpaRepository<AllBots,Long> {
    Optional<AllBots> findTokenByToken(String token);
}
