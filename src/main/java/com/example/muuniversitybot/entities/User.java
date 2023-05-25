package com.example.muuniversitybot.entities;

import com.example.muuniversitybot.utils.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String chatId;
    private String realId;
    private String phoneNumber;
    private String step;
    private String activeRole;
    private String applicantRole;
    private String adminRole;
    private String superAdminRole;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
}
