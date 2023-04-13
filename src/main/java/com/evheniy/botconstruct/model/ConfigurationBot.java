package com.evheniy.botconstruct.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class ConfigurationBot {
    @Id
    private Long id;
    private String greetingMessage;
    private String helpMessage;
    private String imageURL;
    private float longitude;
    private float latitude;
    private String codeFromBD;
    @OneToOne(mappedBy = "configurationBot")
    private Token token;


}
