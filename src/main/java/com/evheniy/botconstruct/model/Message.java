package com.evheniy.botconstruct.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "bots_data_id")
    private BotsData botsData;

    @ManyToOne
    @JoinColumn(name = "botUser_id")
    private BotUser botUser;
}