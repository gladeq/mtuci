package ru.mtuci.rbpomtuci2024.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mtuci.rbpomtuci2024.model.ApplicationRole;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUser {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password_hash;
    private String email;

    @Enumerated(EnumType.STRING)
    private ApplicationRole role;

}
