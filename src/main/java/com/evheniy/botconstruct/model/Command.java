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
    @JoinColumn(name = "botsData_id")
    private BotsData botsData;
    @Column(columnDefinition = "TEXT") // Використовуємо TEXT для зберігання великого об'єму тексту
    private String json; // Поле для зберігання JSON-інформації

    private String typeCommand; // Поле для зберігання типу команди
}
