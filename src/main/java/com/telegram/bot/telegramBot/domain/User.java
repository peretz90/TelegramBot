package com.telegram.bot.telegramBot.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
public class User extends AbstractEntityListener {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private Long chatId;

}
