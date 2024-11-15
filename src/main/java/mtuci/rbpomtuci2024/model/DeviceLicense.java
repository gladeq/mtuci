package mtuci.rbpomtuci2024.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mtuci.rbpomtuci2024.model.Device;
import mtuci.rbpomtuci2024.model.License;

import java.util.Date;

@Setter
@Getter
@Entity
public class DeviceLicense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "license_id")
    private License license;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

    private Date activationDate;


}

