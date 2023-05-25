package com.example.muuniversitybot.service;

import com.example.muuniversitybot.entities.User;
import com.example.muuniversitybot.repository.UserRepository;
import com.example.muuniversitybot.utils.Roles;
import com.example.muuniversitybot.utils.Steps;
import com.example.muuniversitybot.utils.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UpdateService updateService;

    public User getUserByChatId(String chatId) {
        return userRepository.findByChatId(chatId)
                .orElse(new User());
    }

    public User saveOrGetUser(Update update) {
        String chatI = updateService.chatId(update);
        if (userRepository.existsByChatId(chatI)) {
            return getUserByChatId(chatI);
        }

        User user = new User();
        String firstName = update.getMessage().getFrom().getFirstName();
        String lastName = update.getMessage().getFrom().getLastName();
        lastName = lastName != null ? lastName : "";
        user.setName(firstName + " " + lastName);
        user.setUsername(update.getMessage().getFrom().getUserName());
        user.setUserStatus(UserStatus.NEW);
        user.setChatId(chatI);
        user.setRealId(update.getMessage().getFrom().getId().toString());
        user.setStep(Steps.STARTED);
        user.setActiveRole(Roles.APPLICANT);
        user.setApplicantRole(Roles.APPLICANT);
        return userRepository.save(user);
    }

    public User makeApplicantSuperAdmin(User user){
        user.setActiveRole(Roles.SUPER_ADMIN);
        user.setAdminRole(Roles.ADMIN);
        user.setSuperAdminRole(Roles.SUPER_ADMIN);
        return userRepository.save(user);
    }

}
