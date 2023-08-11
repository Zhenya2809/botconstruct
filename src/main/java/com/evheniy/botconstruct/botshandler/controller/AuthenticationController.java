package com.evheniy.botconstruct.botshandler.controller;

import com.evheniy.botconstruct.botshandler.authentication.AuthenticationRequest;
import com.evheniy.botconstruct.botshandler.authentication.AuthenticationResponse;
import com.evheniy.botconstruct.botshandler.authentication.AuthenticationService;
import com.evheniy.botconstruct.botshandler.authentication.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return authenticationService.register(request);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));

    }

    @PostMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("hi");

    }
}
