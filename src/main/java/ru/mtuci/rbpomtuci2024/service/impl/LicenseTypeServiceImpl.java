package ru.mtuci.rbpomtuci2024.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mtuci.rbpomtuci2024.Repository.LicenseRepository;
import ru.mtuci.rbpomtuci2024.Repository.LicenseTypeRepository;
import ru.mtuci.rbpomtuci2024.model.License;
import ru.mtuci.rbpomtuci2024.model.LicenseType;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LicenseTypeServiceImpl {

    private final LicenseTypeRepository licenseTypeRepository;
    private final LicenseRepository licenseRepository;

    public List<LicenseType> getAllLicenseTypes() {
        return licenseTypeRepository.findAll();
    }

    public LicenseType getLicenseTypeById(Long id) {
        return licenseTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("LicenseType not found with id: " + id));
    }

    public LicenseType createLicenseType(LicenseType licenseType) {
        // Проверка уникальности ID
        if (licenseTypeRepository.existsById(licenseType.getId())) {
            throw new IllegalArgumentException("License type with ID " + licenseType.getId() + " already exists");
        }
        return licenseTypeRepository.save(licenseType);
    }

    public LicenseType updateLicenseType(Long id, LicenseType updatedLicenseType) {
        LicenseType licenseType = getLicenseTypeById(id);
        licenseType.setName(updatedLicenseType.getName());
        licenseType.setDefaultDuration(updatedLicenseType.getDefaultDuration());
        licenseType.setDescription(updatedLicenseType.getDescription());
        return licenseTypeRepository.save(licenseType);
    }

    public void deleteLicenseType(Long id) {
        List<License> licenses = licenseRepository.findByTypeId(id);
        if (!licenses.isEmpty()) {
            licenseRepository.deleteAll(licenses);
        }
        licenseTypeRepository.deleteById(id);
    }
}
