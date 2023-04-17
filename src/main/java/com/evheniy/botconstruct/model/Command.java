package com.evheniy.botconstruct.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Command {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String commandText;
    private String replyText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tBots_id")
    private TBots tBots;
}
