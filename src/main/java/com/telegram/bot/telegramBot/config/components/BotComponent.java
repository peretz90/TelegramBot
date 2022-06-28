package com.telegram.bot.telegramBot.config.components;

import com.telegram.bot.telegramBot.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
public class BotComponent extends TelegramLongPollingBot {

    private final UserServiceImpl userService;
    @Value("${telegram.username}")
    private String telegramUsername;

    @Value("${telegram.token}")
    private String telegramToken;


    @Override
    public String getBotUsername() {
        return telegramUsername;
    }

    @Override
    public String getBotToken() {
        return telegramToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            SendMessage sendMessage = new SendMessage();
            Long currentChatId = update.getMessage().getChatId();
            String currentUsername = update.getMessage().getFrom().getUserName();
            if (currentUsername == null) {
                currentUsername = update.getMessage().getFrom().getFirstName() + " + " + update.getMessage().getFrom().getLastName();
            }

            sendMessage.setChatId(String.valueOf(currentChatId));
            switch (update.getMessage().getText()) {
                case "/start" -> {
                    userService.createUser(currentUsername, currentChatId);
                    sendMessage.setText("""
                            Hello!!!
                            I'm your info bot
                            please send /lk""");
                }
                case "/lk" -> sendMessage.setText("""
                        Ваше имя: "%s" 
                        Ваш уникальный ID: "%s" """.formatted(currentUsername, currentChatId));
                default -> sendMessage.setText("""
                        Invalid command!
                        Please send /start""");
            }
            execute(sendMessage);
            } catch (TelegramApiException ex) {
            ex.printStackTrace();
        }
    }
}
