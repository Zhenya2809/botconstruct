package com.evheniy.botconstruct.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "bots_users")
public class BotUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long chatId;

    private String firstName;

    private String lastName;

    private String globalState;




    @JsonBackReference
    @OneToMany(mappedBy = "botUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> messages;
}