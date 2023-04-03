package com.example.muuniversitybot.entity;

import com.example.muuniversitybot.util.RoleUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String realId;
    private String chatId;
    private Boolean isUser = true;
    private Boolean isAdmin = false;
    private String onProfile = RoleUtils.USER;
    private String step;

}
