package com.example.muuniversitybot.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class ButtonService {
    public ReplyKeyboardMarkup createButton(String buttonName) {
        return ReplyKeyboardMarkup.builder()
                .resizeKeyboard(true)
                .keyboardRow(new KeyboardRow(new ArrayList<>(Arrays.asList(new KeyboardButton(buttonName))))).build();
    }

    public ReplyKeyboardMarkup createButtonShareContact(){
        return ReplyKeyboardMarkup.builder()
                .resizeKeyboard(true)
                .keyboardRow(new KeyboardRow(Arrays.asList(KeyboardButton.builder()
                                .requestContact(true)
                                .text("Telefon Raqam \uD83D\uDCDE")
                        .build())))
                .build();
    }

    public ReplyKeyboardMarkup createButtons(int countPerLine, List<String> buttonNames) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        int i = 0;
        int counter = 0;
        int qoldiq = buttonNames.size() % countPerLine;
        int size = buttonNames.size();
        for (String name : buttonNames) {
            keyboardRow.add(name);
            i++;
            if (i == countPerLine || (size - counter == qoldiq && i == qoldiq)) {
                keyboardRowList.add(keyboardRow);
                keyboardRow = new KeyboardRow();
                counter += i;
                i = 0;
            }
        }
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        return replyKeyboardMarkup;
    }




}
