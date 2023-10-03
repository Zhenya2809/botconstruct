package com.evheniy.botconstruct.botshandler.controller;

import com.evheniy.botconstruct.Service.impl.BotsDataService;
import com.evheniy.botconstruct.model.BotsData;
import com.evheniy.botconstruct.model.dto.BotsDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/setting_bot")
public class SettingBotController {

    @Autowired
    BotsDataService botsDataService;


    @GetMapping("/getAllBots")
    public ResponseEntity<List<BotsDataDto>> getAllBots() {
        List<BotsDataDto> botsDataDto = botsDataService.findUserBots();
        return new ResponseEntity<>(botsDataDto, HttpStatus.OK);

    }
}
