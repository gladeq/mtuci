package ru.mtuci.rbpomtuci2024.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mtuci.rbpomtuci2024.model.ApplicationUser;
import ru.mtuci.rbpomtuci2024.model.Device;
import ru.mtuci.rbpomtuci2024.model.License;
import ru.mtuci.rbpomtuci2024.model.Ticket;
import ru.mtuci.rbpomtuci2024.service.DeviceService;
import ru.mtuci.rbpomtuci2024.service.UserService;
import ru.mtuci.rbpomtuci2024.service.impl.DeviceLicenseServiceImpl;
import ru.mtuci.rbpomtuci2024.service.impl.LicenseHistoryServiceImpl;
import ru.mtuci.rbpomtuci2024.service.impl.LicenseServiceImpl;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/activation")
public class ActivationController {

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private LicenseServiceImpl licenseService;
    @Autowired
    private UserService userService;
    @Autowired
    private DeviceLicenseServiceImpl deviceLicenseService;
    @Autowired
    private LicenseHistoryServiceImpl licenseHistoryService;

    @PostMapping
    public ResponseEntity<Ticket> activateLicense(@RequestBody Map<String, String> request) {
        String activationCode = request.get("activationCode");
        String deviceId = request.get("deviceId");
        String email = request.get("email");

        if (activationCode == null || deviceId == null || email == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        ApplicationUser user = userService.findOrCreateUser(email);
        Device device = deviceService.registerOrUpdateDevice(deviceId, user); // Передаем объект user

        Optional<License> licenseOptional = licenseService.findLicenseByActivationCode(activationCode);
        if (licenseOptional.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        License license = licenseOptional.get();

        if (license.getUser() == null) {
            license.setUser(user);
            licenseService.updateLicense(license);
        }

        try {
            licenseService.validateActivation(license, device, user);
            deviceLicenseService.createDeviceLicense(license, device);
            licenseService.updateLicense(license);
            licenseHistoryService.recordLicenseChange(license, user, "Активирована", "Лицензия активируется пользователем по электронной почте: " + email);

            Long userId = user.getId();
            Ticket ticket = deviceLicenseService.generateTicket(license, device, userId);

            return new ResponseEntity<>(ticket, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}