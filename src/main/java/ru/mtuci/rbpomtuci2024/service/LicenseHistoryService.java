package ru.mtuci.rbpomtuci2024.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mtuci.rbpomtuci2024.Repository.LicenseHistoryRepository;
import ru.mtuci.rbpomtuci2024.model.LicenseHistory;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LicenseHistoryService {

    private final LicenseHistoryRepository licenseHistoryRepository;

    public List<LicenseHistory> getAllLicenseHistories() {
        return licenseHistoryRepository.findAll();
    }

    public LicenseHistory getLicenseHistoryById(Long id) {
        return licenseHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("LicenseHistory not found with id: " + id));
    }

    public LicenseHistory createLicenseHistory(LicenseHistory licenseHistory) {
        return licenseHistoryRepository.save(licenseHistory);
    }

    public LicenseHistory updateLicenseHistory(Long id, LicenseHistory updatedLicenseHistory) {
        LicenseHistory licenseHistory = getLicenseHistoryById(id);
        licenseHistory.setLicense(updatedLicenseHistory.getLicense());
        licenseHistory.setUser(updatedLicenseHistory.getUser());
        licenseHistory.setStatus(updatedLicenseHistory.getStatus());
        licenseHistory.setChangeDate(updatedLicenseHistory.getChangeDate());
        licenseHistory.setDescription(updatedLicenseHistory.getDescription());
        return licenseHistoryRepository.save(licenseHistory);
    }

    public void deleteLicenseHistory(Long id) {
        licenseHistoryRepository.deleteById(id);
    }
}
