package com.evheniy.botconstruct.botshandler.controller;

import com.evheniy.botconstruct.Service.impl.BotsDataService;
import com.evheniy.botconstruct.model.CreateBotRequest;
import com.evheniy.botconstruct.model.CreateBotResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/bot")
public class CreateBotController {
    @Autowired
    BotsDataService botsDataService;

    @PostMapping("/create/")
    public CreateBotResponse createBot(@RequestBody CreateBotRequest request) {
        return botsDataService.createBot(request);
    }
}
