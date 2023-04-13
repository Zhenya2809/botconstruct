package com.evheniy.botconstruct.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Token {
    private String token;
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private ConfigurationBot configurationBot;
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
