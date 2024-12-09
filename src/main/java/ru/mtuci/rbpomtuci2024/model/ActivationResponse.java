package ru.mtuci.rbpomtuci2024.model;

import lombok.Getter;

@Getter
public class ActivationResponse {
    // Геттеры, сеттеры
    private String ticket;

    public ActivationResponse(String ticket) {
        this.ticket = ticket;
    }

}
