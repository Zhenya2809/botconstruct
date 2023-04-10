package com.evheniy.botconstruct.repository;

import com.evheniy.botconstruct.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {

}
