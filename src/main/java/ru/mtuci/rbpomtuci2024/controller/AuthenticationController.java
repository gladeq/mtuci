package ru.mtuci.rbpomtuci2024.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mtuci.rbpomtuci2024.configuration.JwtTokenProvider;
import ru.mtuci.rbpomtuci2024.model.ApplicationUser;
import ru.mtuci.rbpomtuci2024.model.ApplicationRole;
import ru.mtuci.rbpomtuci2024.model.AuthenticationRequest;
import ru.mtuci.rbpomtuci2024.model.AuthenticationResponse;
import ru.mtuci.rbpomtuci2024.Repository.UserRepository;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        try {
            String login = request.getEmail();

            ApplicationUser user = userRepository.findByEmail(login)
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login, request.getPassword())
            );

            String token = jwtTokenProvider.createToken(login, user.getRole().getGrantedAuthorities());

            return ResponseEntity.ok(new AuthenticationResponse(login, token));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Неверный адрес электронной почты или пароль");
        }
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<?> register(@RequestBody AuthenticationRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Электронная почта уже существует");
        }


        ApplicationUser user = new ApplicationUser();
        user.setEmail(request.getEmail());
        user.setLogin(request.getLogin());
        user.setPassword_hash(passwordEncoder.encode(request.getPassword()));
        user.setRole(ApplicationRole.USER);




        try {
            user = userRepository.save(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при регистрации: " + e.getMessage());
        }


        return ResponseEntity.ok("Успешная регистрация пользователя или администратора");
    }
}