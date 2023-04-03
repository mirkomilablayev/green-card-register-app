package com.example.muuniversitybot.controller;

import com.example.muuniversitybot.configuration.BotConfiguration;
import com.example.muuniversitybot.entity.User;
import com.example.muuniversitybot.entity.repository.UserRepository;
import com.example.muuniversitybot.service.ButtonService;
import com.example.muuniversitybot.service.UserService;
import com.example.muuniversitybot.util.ButtonUtils;
import com.example.muuniversitybot.util.RoleUtils;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        String chatId = userService.getChatId(update);
        String text = userService.getText(update);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        if (user.getOnProfile().equals(RoleUtils.USER)) {
            if (text.equals("/start")) {
                userStartScenario(sendMessage, user);
                return;
            }

            if (text.equals(ButtonUtils.getResult)){

                return;
            }
            if (text.equals(ButtonUtils.lastYearResult)){

                return;
            }
            if (text.equals(ButtonUtils.resultByDirections)){

                return;
            }
            if (text.equals(ButtonUtils.opportunitiesByScore)){

                return;
            }
            if (text.equals(ButtonUtils.currencyCourse)){

                return;
            }
            if (text.equals(ButtonUtils.prayerTimes)){

                return;
            }
            sendMessageExecutor(sendMessage);
            return;
        } else {

            sendMessageExecutor(sendMessage);
            return;
        }
    }


    private void sendMessageExecutor(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void userStartScenario(SendMessage sendMessage, User currentUser) {
        sendMessage.setText("Asosiy menyu");
        ArrayList<String> buttons = new ArrayList<>(List.of(
                ButtonUtils.getResult,
                ButtonUtils.lastYearResult,
                ButtonUtils.resultByDirections,
                ButtonUtils.opportunitiesByScore,
                ButtonUtils.currencyCourse,
                ButtonUtils.prayerTimes
        ));
        if (currentUser.getIsUser() && currentUser.getIsAdmin()){
            buttons.add(ButtonUtils.changeProfile);
        }
        sendMessage.setReplyMarkup(buttonService.createButtons(
                2,
                buttons
        ));
    }
}
