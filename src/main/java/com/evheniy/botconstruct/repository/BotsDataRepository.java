package com.evheniy.botconstruct.repository;

import com.evheniy.botconstruct.model.BotsData;
import com.evheniy.botconstruct.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BotsDataRepository extends JpaRepository<BotsData,Long> {
    Optional<BotsData> findByToken(String token);
    @NotNull
    Optional<BotsData> findById(@NotNull Long Id);
    List<BotsData> findAllByUser(User user);
}
