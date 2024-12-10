package ru.mtuci.rbpomtuci2024.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mtuci.rbpomtuci2024.model.Device;
import ru.mtuci.rbpomtuci2024.model.DeviceLicense;
import ru.mtuci.rbpomtuci2024.model.License;
import ru.mtuci.rbpomtuci2024.service.impl.DeviceLicenseServiceImpl;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/device-licenses")
@RequiredArgsConstructor
public class DeviceLicenseController {

    private final DeviceLicenseServiceImpl deviceLicenseService;

    @GetMapping
    public ResponseEntity<List<DeviceLicense>> getAllDeviceLicenses() {
        return ResponseEntity.ok(deviceLicenseService.getAllDeviceLicenses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceLicense> getDeviceLicenseById(@PathVariable Long id) {
        return ResponseEntity.ok(deviceLicenseService.getDeviceLicenseById(id));
    }

    @PostMapping
    public ResponseEntity<DeviceLicense> createDeviceLicense(@RequestBody Map<String, Long> request) {
        Long licenseId = request.get("licenseId");
        Long deviceId = request.get("deviceId");



        License license = new License();
        license.setId(licenseId);

        Device device = new Device();
        device.setId(deviceId);

        DeviceLicense createdDeviceLicense = deviceLicenseService.createDeviceLicense(license, device);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdDeviceLicense);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceLicense> updateDeviceLicense(@PathVariable Long id,
                                                             @RequestBody DeviceLicense deviceLicense) {
        return ResponseEntity.ok(deviceLicenseService.updateDeviceLicense(id, deviceLicense));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeviceLicense(@PathVariable Long id) {
        deviceLicenseService.deleteDeviceLicense(id);
        return ResponseEntity.noContent().build();
    }
}