package mtuci.rbpomtuci2024.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mtuci.rbpomtuci2024.model.Demo;

import java.util.List;

@Entity
@Table(name = "info")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Info {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @ManyToMany(mappedBy = "infos")
    private List<Demo> demos;
}

