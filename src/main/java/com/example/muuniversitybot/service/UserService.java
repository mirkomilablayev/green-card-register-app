package com.example.muuniversitybot.service;

import com.example.muuniversitybot.entity.User;
import com.example.muuniversitybot.entity.repository.UserRepository;
import com.example.muuniversitybot.util.RoleUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User cretaeOrGetUser(Update update) {
        String realId = getRealId(update);
        org.telegram.telegrambots.meta.api.objects.User tgUser = getUser(update);
        String chatId = getChatId(update);

        Optional<User> userOptional = userRepository.findByRealId(realId);
        if (!userOptional.isPresent()) {
            User user = new User();
            user.setIsUser(true);
            user.setIsAdmin(true);
            user.setChatId(chatId);
            user.setIsAdmin(false);
            user.setOnProfile(RoleUtils.USER);
            tgUser.getFirstName();
            tgUser.getLastName();
            String name = tgUser.getLastName();
            user.setFullName(name);
            return userRepository.save(user);
        }
        return userOptional.get();

    }


    public String getRealId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getFrom().getId().toString();
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom().getId().toString();
        } else if (update.hasChannelPost()) {
            return update.getChannelPost().getFrom().getId().toString();
        } else if (update.hasChatMember()) {
            return update.getChatMember().getFrom().getId().toString();
        } else if (update.hasChatJoinRequest()) {
            return update.getChatJoinRequest().getUser().getId().toString();
        } else if (update.hasChosenInlineQuery()) {
            return update.getChosenInlineQuery().getFrom().getId().toString();
        } else if (update.hasEditedChannelPost()) {
            return update.getEditedChannelPost().getFrom().getId().toString();
        } else if (update.hasEditedMessage()) {
            return update.getEditedMessage().getFrom().getId().toString();
        } else if (update.hasMyChatMember()) {
            return update.getMyChatMember().getFrom().getId().toString();
        } else if (update.hasInlineQuery()) {
            return update.getInlineQuery().getFrom().getId().toString();
        } else if (update.hasPollAnswer()) {
            return update.getPollAnswer().getUser().getId().toString();
        } else if (update.hasPreCheckoutQuery()) {
            return update.getPreCheckoutQuery().getFrom().getId().toString();
        } else if (update.hasShippingQuery()) {
            return update.getShippingQuery().getFrom().getId().toString();
        }
        return "";
    }

    public org.telegram.telegrambots.meta.api.objects.User getUser(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getFrom();
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom();
        } else if (update.hasChannelPost()) {
            return update.getChannelPost().getFrom();
        } else if (update.hasChatMember()) {
            return update.getChatMember().getFrom();
        } else if (update.hasChatJoinRequest()) {
            return update.getChatJoinRequest().getUser();
        } else if (update.hasChosenInlineQuery()) {
            return update.getChosenInlineQuery().getFrom();
        } else if (update.hasEditedChannelPost()) {
            return update.getEditedChannelPost().getFrom();
        } else if (update.hasEditedMessage()) {
            return update.getEditedMessage().getFrom();
        } else if (update.hasMyChatMember()) {
            return update.getMyChatMember().getFrom();
        } else if (update.hasInlineQuery()) {
            return update.getInlineQuery().getFrom();
        } else if (update.hasPollAnswer()) {
            return update.getPollAnswer().getUser();
        } else if (update.hasPreCheckoutQuery()) {
            return update.getPreCheckoutQuery().getFrom();
        } else if (update.hasShippingQuery()) {
            return update.getShippingQuery().getFrom();
        }
        return new org.telegram.telegrambots.meta.api.objects.User();
    }


    public String getChatId(Update update) {
        return update.getMessage().getChatId().toString();
    }

    public String getText(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getText();
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getText();
        } else if (update.hasChannelPost()) {
            return update.getChannelPost().getText();
        } else if (update.hasEditedChannelPost()) {
            return update.getEditedChannelPost().getText();
        } else if (update.hasEditedMessage()) {
            return update.getEditedMessage().getText();
        }
        return "";
    }


    public String isValidUsername(String username) {
        if (!hasLength(username)) return "Loginni qaytadan kiriting";
        int strLen = username.length();
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(username.charAt(i))) {
                return "Login bitta so'z bo'lishi kerak!!\nMasalan: Dilshod_7777\nTaqiqlanadi: dilshod 777";
            }
        }
        return "0";
    }


    public boolean hasLength(String str) {
        return (str != null && str.length() > 0);
    }
}
