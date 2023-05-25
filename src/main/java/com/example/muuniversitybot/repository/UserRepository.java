package com.example.muuniversitybot.repository;

import com.example.muuniversitybot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByChatId(String chatId);
    Boolean existsByChatId(String chatId);
}
