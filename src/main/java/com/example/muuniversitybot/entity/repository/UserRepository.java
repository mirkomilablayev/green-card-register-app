package com.example.muuniversitybot.entity.repository;

import com.example.muuniversitybot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByRealId(String realId);
}
