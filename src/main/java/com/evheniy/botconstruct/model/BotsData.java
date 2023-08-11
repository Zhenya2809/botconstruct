package com.evheniy.botconstruct.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
public class BotsData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(mappedBy = "botsData", cascade = CascadeType.ALL)
    private ConfigurationBot configurationBot;

    @OneToMany(mappedBy = "botsData", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Command> commands;

    @Enumerated(EnumType.STRING)
    private BotType botType;

    private String webhookUrl;

    private String botName;

    private String botAvatarUrl;

    @OneToMany(mappedBy = "botsData", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Message> messages;

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", configurationBot=" + (configurationBot != null ? configurationBot.getId() : "null") +
                '}';
    }
}