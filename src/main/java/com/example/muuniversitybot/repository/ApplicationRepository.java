package com.example.muuniversitybot.repository;

import com.example.muuniversitybot.entities.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
