package com.evheniy.botconstruct.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "botUser_id")
    private BotUser botUser;
}