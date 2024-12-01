package ru.mtuci.rbpomtuci2024.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mtuci.rbpomtuci2024.model.LicenseHistory;
import ru.mtuci.rbpomtuci2024.service.LicenseHistoryService;

import java.util.List;

@RestController
@RequestMapping("/api/license-histories")
@RequiredArgsConstructor
public class LicenseHistoryController {

    private final LicenseHistoryService licenseHistoryService;

    @GetMapping
    public ResponseEntity<List<LicenseHistory>> getAllLicenseHistories() {
        return ResponseEntity.ok(licenseHistoryService.getAllLicenseHistories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LicenseHistory> getLicenseHistoryById(@PathVariable Long id) {
        return ResponseEntity.ok(licenseHistoryService.getLicenseHistoryById(id));
    }

    @PostMapping
    public ResponseEntity<LicenseHistory> createLicenseHistory(@RequestBody LicenseHistory licenseHistory) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(licenseHistoryService.createLicenseHistory(licenseHistory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LicenseHistory> updateLicenseHistory(@PathVariable Long id,
                                                               @RequestBody LicenseHistory licenseHistory) {
        return ResponseEntity.ok(licenseHistoryService.updateLicenseHistory(id, licenseHistory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLicenseHistory(@PathVariable Long id) {
        licenseHistoryService.deleteLicenseHistory(id);
        return ResponseEntity.noContent().build();
    }
}
