package com.evheniy.botconstruct.repository;


import com.evheniy.botconstruct.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhone(String phone);

    @Query("SELECT u FROM User u WHERE LOWER(CONCAT(u.firstName, ' ', u.lastName)) LIKE %?1%")
    List<User> searchUsersByName(String name);

    Optional<User> findUserByPhone(String phone);

    Optional<User> findByFirstNameAndLastName(String firstName, String lastName);
}