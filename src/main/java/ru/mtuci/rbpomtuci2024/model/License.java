package ru.mtuci.rbpomtuci2024.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
public class License {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;

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
    
}