package com.example.muuniversitybot.service;

import com.example.muuniversitybot.configuration.BotConfiguration;
import com.example.muuniversitybot.entities.User;
import com.example.muuniversitybot.repository.ApplicationRepository;
import com.example.muuniversitybot.repository.UserRepository;
import com.example.muuniversitybot.utils.Roles;
import com.example.muuniversitybot.utils.Steps;
import com.example.muuniversitybot.utils.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@RequiredArgsConstructor
@Component
public class BotExecutor extends TelegramLongPollingBot {


    private final BotConfiguration botConfiguration;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;
    private final UserService userService;
    private final ButtonService buttonService;
    private final UpdateService updateService;


    @Override
    public String getBotUsername() {
        return this.botConfiguration.getUsername();
    }

    @Override
    public String getBotToken() {
        return this.botConfiguration.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        User user = userService.saveOrGetUser(update);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(user.getChatId());
        if (user.getActiveRole().equals(Roles.SUPER_ADMIN)) {
            superAdminPage(update, user,sendMessage);
        } else if (user.getActiveRole().equals(Roles.ADMIN)) {

        } else {
            userPage(update, user, sendMessage);
        }
    }

    private void superAdminPage(Update update, User user, SendMessage sendMessage) {
        String messageText = updateService.getMessageText(update);
        if (messageText != null && messageText.equals("/start")){
            clickSuperAdminStartResponse(user, sendMessage);
        }
    }

    private void clickSuperAdminStartResponse(User user, SendMessage sendMessage) {
        user.setStep(Steps.SA_SEND_MAIN_MENU);
        userRepository.save(user);
        sendMessage.setText("Super admin asosiy menyusi");
        sendMessage.setReplyMarkup(buttonService.createButtons(1, List.of(
                "Application Requests", "Adminstrators", "Payed Applications"
        )));
    }

    public void userPage(Update update, User user, SendMessage sendMessage) {

        String messageText = updateService.getMessageText(update);
        if (messageText != null && messageText.equals("/start")) {
            if (user.getUserStatus().equals(UserStatus.NEW)) {
                clickStartResponse(user, sendMessage);
            } else {
                mainMenu(sendMessage);
            }
            return;
        }
        if (user.getStep().equals(Steps.STARTED)) {
            clickStartResponse(user, sendMessage);
            return;
        }

        if (user.getStep().equals(Steps.SEND_SHARE_CONTACT)) {
            String contact = updateService.getContact(update);
            user.setPhoneNumber(contact);
            user.setUserStatus(UserStatus.ONLY_CLIENT);
            user.setStep(Steps.SEND_MAIN_MENU);
            userRepository.save(user);
            mainMenu(sendMessage);
            return;
        }

        assert messageText != null;
        if (messageText.length() > 30) {
            String[] split = messageText.split("-");
            if (split.length == 2) {
                if (botConfiguration.getAdminSecret().equals(split[0]) && botConfiguration.getAdminSecretPassword().equals(split[1])) {
                    userService.makeApplicantSuperAdmin(user);
                    sendMessage.setText("Siz endi super administratorsiz\n" +
                            "botni qayta tushirish uchun /start tugmachasini bosing\n\n/start");
                    sendMessageExecutor(sendMessage);
                    return;
                }
            }
        }

        sendMessage.setText("Xato buyruq kiritildi ☹️☹️");
        sendMessageExecutor(sendMessage);
        return;
    }

    private void mainMenu(SendMessage sendMessage) {
        sendMessage.setText("Siz muvaffaqqiyatli ro'yxatdan o'tdingiz \uD83E\uDD73, Endi siz quyidagi bo'limlardan birini tanlashingiz mumkin \uD83D\uDC47");
        sendMessage.setReplyMarkup(buttonService.createButtons(2, List.of("Green Card uchun ariza", "Bepul qo'llanma")));
        sendMessageExecutor(sendMessage);
    }

    private void clickStartResponse(User user, SendMessage sendMessage) {
        sendMessage.setText("Telefon raqamingizni yuboring");
        sendMessage.setReplyMarkup(buttonService.createButtonShareContact());
        sendMessageExecutor(sendMessage);
        user.setStep(Steps.SEND_SHARE_CONTACT);
        userRepository.save(user);
    }

    private void sendMessageExecutor(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


}
