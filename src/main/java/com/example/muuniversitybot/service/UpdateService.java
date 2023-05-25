package com.example.muuniversitybot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class UpdateService {

    public String chatId(Update update) {
        if (update.hasCallbackQuery())
            return update.getCallbackQuery().getMessage().getChatId().toString();
        return update.getMessage().getChatId().toString();
    }

    public String getContact(Update update) {
        return update.getMessage().getContact().getPhoneNumber();
    }

    public String getMessageText(Update update) {
        if (update.hasMessage()) {
            String text = update.getMessage().getText();
            return text == null ? "" : text;
        }
        return "";
    }

}
