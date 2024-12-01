package ru.mtuci.rbpomtuci2024.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mtuci.rbpomtuci2024.Repository.LicenseRepository;
import ru.mtuci.rbpomtuci2024.model.License;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LicenseService {

    private final LicenseRepository licenseRepository;

    public List<License> getAllLicenses() {
        return licenseRepository.findAll();
    }

    public License getLicenseById(Long id) {
        return licenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("License not found with id: " + id));
    }

    public License createLicense(License license) {
        return licenseRepository.save(license);
    }

    public License updateLicense(Long id, License updatedLicense) {
        License license = getLicenseById(id);
        license.setCode(updatedLicense.getCode());
        license.setUser(updatedLicense.getUser());
        license.setProduct(updatedLicense.getProduct());
        license.setType(updatedLicense.getType());
        license.setFirstActivationDate(updatedLicense.getFirstActivationDate());
        license.setEndingDate(updatedLicense.getEndingDate());
        license.setBlocked(updatedLicense.isBlocked());
        license.setDeviceCount(updatedLicense.getDeviceCount());
        license.setOwner(updatedLicense.getOwner());
        license.setDuration(updatedLicense.getDuration());
        license.setDescription(updatedLicense.getDescription());
        return licenseRepository.save(license);
    }

    public void deleteLicense(Long id) {
        licenseRepository.deleteById(id);
    }
}
