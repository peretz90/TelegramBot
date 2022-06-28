package com.telegram.bot.telegramBot.services;

import com.telegram.bot.telegramBot.domain.User;
import com.telegram.bot.telegramBot.repository.UserRepo;
import com.telegram.bot.telegramBot.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceImpl {

    private final UserRepo userRepo;

    @Override
    public void createUser(String username, Long chatId) {

        if (userRepo.findByUsername(username) == null) {
            User user = new User();
            user.setUsername(username);
            user.setChatId(chatId);
            userRepo.save(user);
        }

    }
}
