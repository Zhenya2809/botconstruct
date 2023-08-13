package com.evheniy.botconstruct.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public  class SocialCommandRequest {
    private Long botId;
    private Long userId;


}