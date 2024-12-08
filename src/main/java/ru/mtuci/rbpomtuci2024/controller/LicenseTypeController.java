package ru.mtuci.rbpomtuci2024.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mtuci.rbpomtuci2024.model.LicenseType;
import ru.mtuci.rbpomtuci2024.service.impl.LicenseTypeServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/license-types")
@RequiredArgsConstructor
public class LicenseTypeController {

    private final LicenseTypeServiceImpl licenseTypeService;

    @GetMapping
    public ResponseEntity<List<LicenseType>> getAllLicenseTypes() {
        return ResponseEntity.ok(licenseTypeService.getAllLicenseTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LicenseType> getLicenseTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(licenseTypeService.getLicenseTypeById(id));
    }

    @PostMapping
    public ResponseEntity<LicenseType> createLicenseType(@RequestBody LicenseType licenseType) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(licenseTypeService.createLicenseType(licenseType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LicenseType> updateLicenseType(@PathVariable Long id, @RequestBody LicenseType licenseType) {
        return ResponseEntity.ok(licenseTypeService.updateLicenseType(id, licenseType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLicenseType(@PathVariable Long id) {
        licenseTypeService.deleteLicenseType(id);
        return ResponseEntity.noContent().build();
    }
}
