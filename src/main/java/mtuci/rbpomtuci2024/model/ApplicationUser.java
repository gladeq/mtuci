package mtuci.rbpomtuci2024.model;

import mtuci.rbpomtuci2024.model.ApplicationRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUser {

    @Id
    @GeneratedValue
    private UUID id;

    private String username;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private ApplicationRole role;

    public void setRoles(Set<ApplicationRole> singleton) {
    }
}

