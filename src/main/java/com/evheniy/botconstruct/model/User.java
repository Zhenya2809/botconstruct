package com.evheniy.botconstruct.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "constrUser")
public class User {
    @Id
    @Column(name = "chatId", nullable = false)
    private Long chatId;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;

    @Column(name = "globalState")
    public String globalState;

    public User(Long chatId, String firstName, String lastName) {
        this.chatId = chatId;
        this.firstName = firstName;
        this.lastName = lastName;
    }


//    @Column(name = "localState")
//    private String localeState;
//    @Column(name = "email")
//    private String email;
//    @Column(name = "phone")
//    private String phone;
//    @Column(name = "role", columnDefinition = "varchar(20) default 'USER'")
//    private String role;
//    public enum botstate {
//        ONE,
//        TWO,
//    }


    @Override
    public String toString() {
        return String.format("chatID:: ,%s, firstName:: ,%s,  lastName: ,%s,", this.chatId, this.firstName, this.lastName);
    }
}
