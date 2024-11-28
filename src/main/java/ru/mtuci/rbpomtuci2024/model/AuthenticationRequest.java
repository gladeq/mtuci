package ru.mtuci.rbpomtuci2024.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationRequest {
    private String email;
    private String login;
    private String password;
}

