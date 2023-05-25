package com.example.muuniversitybot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "app_documents")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    private String firstname;
    private String lastname;
    private String gender;
    private String birthCountry;
    private String pictureUrl;
    private String region;
    private String district;
    private String currentlyLivingCountry;
    private String phoneNumber;
    private String familyStatus;
    private int numberOfChild;
}
