package com.evheniy.botconstruct.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateBotResponse {
    private String status;
    private String message;
}
