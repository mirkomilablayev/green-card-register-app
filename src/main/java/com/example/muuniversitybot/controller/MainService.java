package com.example.muuniversitybot.controller;

import com.example.muuniversitybot.configuration.BotConfiguration;
import com.example.muuniversitybot.entity.User;
import com.example.muuniversitybot.entity.repository.UserRepository;
import com.example.muuniversitybot.service.ButtonService;
import com.example.muuniversitybot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class MainService extends TelegramLongPollingBot {


    private final BotConfiguration botConfiguration;
    private final UserRepository userRepository;
    private final ButtonService buttonService;
    private final UserService userService;

    @Override
    public String getBotUsername() {
        return this.botConfiguration.getUsername();
    }

    @Override
    public String getBotToken() {
        return this.botConfiguration.getToken();
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        User user = userService.cretaeOrGetUser(update);
        if (user.getIsUser() && user.getIsAdmin()){

        }else if (user.getIsAdmin()){

        }else if (user.getIsUser()){

        }else {

        }
    }
}
