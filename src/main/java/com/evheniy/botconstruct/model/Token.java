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
}
