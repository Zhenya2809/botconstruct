package com.evheniy.botconstruct.repository;

import com.evheniy.botconstruct.model.BotsData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AllBotsRepository extends JpaRepository<BotsData,Long> {
    Optional<BotsData> findTokenByToken(String token);
}
