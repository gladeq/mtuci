package ru.mtuci.rbpomtuci2024.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.mtuci.rbpomtuci2024.Repository.DeviceLicenseRepository;
import ru.mtuci.rbpomtuci2024.model.ApplicationUser;
import ru.mtuci.rbpomtuci2024.model.Device;
import ru.mtuci.rbpomtuci2024.model.DeviceLicense;
import ru.mtuci.rbpomtuci2024.model.License;
import ru.mtuci.rbpomtuci2024.model.Ticket;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceLicenseServiceImpl {

    private static final String HMAC_ALGO = "HmacSHA256";
    @Value("${security.secret-key}")
    private String secretKey;

    private final DeviceLicenseRepository deviceLicenseRepository;

    public List<DeviceLicense> getAllDeviceLicenses() {
        return deviceLicenseRepository.findAll();
    }

    public DeviceLicense getDeviceLicenseById(Long id) {
        return deviceLicenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Лицензия устройства с id не найдена: " + id));
    }

    public DeviceLicense createDeviceLicense(License license, Device device) {
        DeviceLicense deviceLicense = new DeviceLicense();
        deviceLicense.setDevice(device);
        deviceLicense.setLicense(license);
        deviceLicense.setActivationDate(new Date());
        return deviceLicenseRepository.save(deviceLicense);
    }

    public Ticket generateTicket(License license, Device device, Long userId) {
        Date serverDate = new Date();
        long lifetime = calculateLifetime();
        Date activationDate = license.getFirstActivationDate();
        Date expirationDate = license.getEndingDate();

        String deviceId = device.getMacAddress();
        boolean licenseLocked = license.isBlocked();
        String digitalSignature = generateDigitalSignature(serverDate, lifetime, activationDate, expirationDate, userId, deviceId, licenseLocked);

        return new Ticket(
                serverDate,
                lifetime,
                activationDate,
                expirationDate,
                userId,
                deviceId,
                licenseLocked,
                digitalSignature
        );
    }

    private long calculateLifetime() {
        return 1;  // Время жизни в днях
    }

    private String generateDigitalSignature(Date serverDate, long lifetime, Date activationDate, Date expirationDate, Long userId, String deviceId, boolean licenseLocked) {
        if (secretKey == null || secretKey.isEmpty()) {
            throw new IllegalStateException("Секретный ключ не установлен");
        }

        try {
            Mac mac = Mac.getInstance(HMAC_ALGO);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), HMAC_ALGO);
            mac.init(secretKeySpec);

            String data = serverDate.toString() + lifetime + activationDate.toString() + expirationDate.toString() + userId + deviceId + licenseLocked;
            byte[] hmacData = mac.doFinal(data.getBytes());

            return Base64.getEncoder().encodeToString(hmacData);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось сгенерировать цифровую подпись", e);
        }
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