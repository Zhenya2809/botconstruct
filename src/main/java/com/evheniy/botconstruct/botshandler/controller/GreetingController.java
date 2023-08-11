package com.evheniy.botconstruct.botshandler.controller;

import com.evheniy.botconstruct.model.socket.Greeting;
import com.evheniy.botconstruct.model.socket.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;


@RestController
@RequestMapping("/")
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greet(HelloMessage message) throws InterruptedException {

        Thread.sleep(2000);
        return new Greeting("Hello, " +
                HtmlUtils.htmlEscape(message.getName()));
    }
}