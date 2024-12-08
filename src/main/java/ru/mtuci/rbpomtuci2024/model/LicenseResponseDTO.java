package ru.mtuci.rbpomtuci2024.model;

import lombok.Data;

@Data
public class LicenseResponseDTO {
    private Long id;
    private String code;
    private UserResponseDTO owner;
    private ProductResponseDTO product;
    private LicenseTypeResponseDTO licenseType;
    private Integer duration;
    private String description;
}
