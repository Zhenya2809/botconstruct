package com.evheniy.botconstruct.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.util.List;


@Entity
@Data
public class Message {
    @Id
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
}
