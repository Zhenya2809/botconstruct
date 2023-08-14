package com.evheniy.botconstruct.botshandler.authentication;

import com.evheniy.botconstruct.botshandler.config.JwtService;
import com.evheniy.botconstruct.model.Role;
import com.evheniy.botconstruct.model.User;
import com.evheniy.botconstruct.repository.UserRepository;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> register(RegisterRequest request) {
        Optional<User> userByPhone = repository.findByPhone(request.getPhone());
        if (userByPhone.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                      .body("Вибачте, але цей номер телефону вже зареєстрований в нашій системі. " +
                            "Якщо ви забули свій пароль, ви можете скористатись опцією відновлення паролю." +
                            "Якщо ви вважаєте, що це помилка, будь ласка, зв'яжіться з нашою службою підтримки за адресою support@example.com." +
                            "Дякуємо за розуміння!");
        } else {
            var user = User.builder()
                    .phone(request.getPhone())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();
            repository.save(user);
//            var jwtToken = jwtService.generateJwtToken(user);
//            return ResponseEntity.ok(AuthenticationResponse.builder()
//                    .token(jwtToken)
//                    .build());
            return ResponseEntity.ok("Registration successful");
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getPhone(),request.getPassword()));
        var user = repository.findByPhone(request.getPhone())
                .orElseThrow();
        var jwtToken = jwtService.generateJwtToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
