package com.evheniy.botconstruct.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class TBots {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;
    @OneToOne(mappedBy = "tbots", cascade = CascadeType.ALL)
    private ConfigurationBot configurationBot;

    @OneToMany(mappedBy = "tBots", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Command> commands;

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