package com.evheniy.botconstruct.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ConfigurationBot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String greetingMessage;
    private String helpMessage;
    private String imageURL;
    private float longitude;
    private float latitude;
    private String codeFromBD;
    @OneToOne
    @JoinColumn(name = "botsData_id", referencedColumnName = "id")
    private BotsData botsData;


}
