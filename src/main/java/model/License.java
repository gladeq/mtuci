package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "licenses") // Название таблицы в базе данных
public class License {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Getter
    @Setter
    @Column(name = "activation_date")
    private LocalDate activationDate;

    @Getter
    @Setter
    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    @Setter
    @Getter
    @Column(name = "device_id")
    private String deviceId;


    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

}
