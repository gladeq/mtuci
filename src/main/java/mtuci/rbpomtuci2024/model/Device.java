package mtuci.rbpomtuci2024.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

@Setter
@Getter
@Entity
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String macAddress;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}

