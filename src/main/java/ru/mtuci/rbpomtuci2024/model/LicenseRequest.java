package ru.mtuci.rbpomtuci2024.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class LicenseRequest {
    private Long productId;
    private Long ownerId;
    private Long licenseTypeId;
    private LicenseParameters parameters;

}
