package ru.mtuci.rbpomtuci2024.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Ticket {
    private Date serverDate;
    private long lifetime;
    private Date activationDate;
    private Date expirationDate;
    private Long userId;  // Изменено с String на Long
    private String deviceId;
    private boolean licenseLocked;
    private String digitalSignature;

    public Ticket(Date serverDate, long lifetime, Date activationDate, Date expirationDate,
                  Long userId, String deviceId, boolean licenseLocked, String digitalSignature) {
        this.serverDate = serverDate;
        this.lifetime = lifetime;
        this.activationDate = activationDate;
        this.expirationDate = expirationDate;
        this.userId = userId;
        this.deviceId = deviceId;
        this.licenseLocked = licenseLocked;
        this.digitalSignature = digitalSignature;
    }

    // Getters and Setters

}