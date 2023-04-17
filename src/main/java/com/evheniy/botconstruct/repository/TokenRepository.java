package com.evheniy.botconstruct.repository;

import com.evheniy.botconstruct.model.TBots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TBots,Long> {
    Optional<TBots> findTokenByToken(String token);
}
