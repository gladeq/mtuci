package ru.mtuci.rbpomtuci2024.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActivationRequest {
    private String activationCode;
    private String deviceId;
    private Long userId;


}
