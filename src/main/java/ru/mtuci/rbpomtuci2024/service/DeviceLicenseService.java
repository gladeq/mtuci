package ru.mtuci.rbpomtuci2024.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mtuci.rbpomtuci2024.Repository.DeviceLicenseRepository;
import ru.mtuci.rbpomtuci2024.model.DeviceLicense;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceLicenseService {

    private final DeviceLicenseRepository deviceLicenseRepository;

    public List<DeviceLicense> getAllDeviceLicenses() {
        return deviceLicenseRepository.findAll();
    }

    public DeviceLicense getDeviceLicenseById(Long id) {
        return deviceLicenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DeviceLicense not found with id: " + id));
    }

    public DeviceLicense createDeviceLicense(DeviceLicense deviceLicense) {
        return deviceLicenseRepository.save(deviceLicense);
    }

    public DeviceLicense updateDeviceLicense(Long id, DeviceLicense updatedDeviceLicense) {
        DeviceLicense deviceLicense = getDeviceLicenseById(id);
        deviceLicense.setLicense(updatedDeviceLicense.getLicense());
        deviceLicense.setDevice(updatedDeviceLicense.getDevice());
        deviceLicense.setActivationDate(updatedDeviceLicense.getActivationDate());
        return deviceLicenseRepository.save(deviceLicense);
    }

    public void deleteDeviceLicense(Long id) {
        deviceLicenseRepository.deleteById(id);
    }
}
