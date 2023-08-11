package com.evheniy.botconstruct.botshandler.controller;

import com.evheniy.botconstruct.botshandler.impl.ViberBaseUpdateHandler;
import com.viber.bot.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/viber/webhook")
public class ViberBotWebhookController {

    private ViberBaseUpdateHandler viberUpdateHandler;


    @PostMapping("/")
    public ResponseEntity<String> handleWebhook(@RequestBody String requestBody) {
        Request request = Request.fromJsonString(requestBody);
    //        viberUpdateHandler.handle(request);
        return ResponseEntity.ok().build();
    }
}
