package com.evheniy.botconstruct.model;


import com.evheniy.botconstruct.BotType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class AllBots {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(mappedBy = "allBots", cascade = CascadeType.ALL)
    private ConfigurationBot configurationBot;

    @OneToMany(mappedBy = "allBots", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Command> commands;

    @Enumerated(EnumType.STRING)
    private BotType botType;





    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", token='" + token + '\'' +
                // Змініть наступний рядок, щоб уникнути рекурсії и
                ", configurationBot=" + (configurationBot != null ? configurationBot.getId() : "null") +
                '}';
    }
}