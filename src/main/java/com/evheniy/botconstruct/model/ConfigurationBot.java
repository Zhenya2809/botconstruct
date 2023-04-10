package com.evheniy.botconstruct.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class ConfigurationBot {
    private String greatingMessage;
    private String helpMessage;
    @Id
    private Long id;
    @OneToOne(mappedBy = "configurationBot")
    private Token token;
}
