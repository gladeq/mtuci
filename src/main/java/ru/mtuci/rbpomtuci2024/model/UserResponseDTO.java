package ru.mtuci.rbpomtuci2024.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String login;
    @Enumerated(EnumType.STRING)
    private ApplicationRole role;
}
