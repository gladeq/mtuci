package ru.mtuci.rbpomtuci2024.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@Entity
public class License {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "license_id_seq")
    @SequenceGenerator(name = "license_id_seq", sequenceName = "license_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private LicenseType type;

    private Date firstActivationDate;
    private Date endingDate;
    private boolean blocked;
    private Integer deviceCount;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private ApplicationUser owner;

    private Integer duration;
    private String description;

    @OneToMany(mappedBy = "license")
    private List<DeviceLicense> deviceLicenses;

    @OneToMany(mappedBy = "license")
    private List<LicenseHistory> licenseHistoryEntries;

    @Column(nullable = false, unique = true)
    private String code;

}
