package ru.mtuci.rbpomtuci2024.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mtuci.rbpomtuci2024.Repository.LicenseHistoryRepository;
import ru.mtuci.rbpomtuci2024.model.ApplicationUser;
import ru.mtuci.rbpomtuci2024.model.License;
import ru.mtuci.rbpomtuci2024.model.LicenseHistory;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LicenseHistoryServiceImpl {

    private final LicenseHistoryRepository licenseHistoryRepository;

    public List<LicenseHistory> getAllLicenseHistories() {
        return licenseHistoryRepository.findAll();
    }

    public LicenseHistory getLicenseHistoryById(Long id) {
        return licenseHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("История лицензии с id не найдена: " + id));
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

    public void recordLicenseChange(License license, ApplicationUser user, String status, String description) {
        LicenseHistory history = new LicenseHistory();
        history.setLicense(license);
        history.setUser(user);
        history.setStatus(status);
        history.setDescription(description);
        history.setChangeDate(new Date());
        licenseHistoryRepository.save(history);
    }
}
