package com.example.muuniversitybot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@ConfigurationPropertiesScan
@SpringBootApplication
public class MyUniversityBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyUniversityBotApplication.class, args);
	}

}
