package ru.mtuci.rbpomtuci2024.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mtuci.rbpomtuci2024.Repository.LicenseRepository;
import ru.mtuci.rbpomtuci2024.Repository.ProductRepository;
import ru.mtuci.rbpomtuci2024.Repository.UserRepository;
import ru.mtuci.rbpomtuci2024.Repository.LicenseTypeRepository;
import ru.mtuci.rbpomtuci2024.service.ActivationException;
import ru.mtuci.rbpomtuci2024.model.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LicenseServiceImpl {

    private final LicenseRepository licenseRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final LicenseTypeRepository licenseTypeRepository;

    public List<License> getAllLicenses() {
        return licenseRepository.findAll();
    }

    public License getLicenseById(Long id) {
        return licenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("License not found with id: " + id));
    }

    public License createLicense(Long productId, Long ownerId, Long licenseTypeId, LicenseParameters parameters) {
        // Получаем продукт по ID
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        // Получаем пользователя по ID
        ApplicationUser owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + ownerId));

        // Получаем тип лицензии по ID
        LicenseType licenseType = licenseTypeRepository.findById(licenseTypeId)
                .orElseThrow(() -> new RuntimeException("License type not found with id: " + licenseTypeId));

        // Создаем новую лицензию
        License license = new License();
        license.setOwner(owner);
        license.setProduct(product);
        license.setType(licenseType);

        // Устанавливаем параметры
        license.setDuration(parameters.getDuration());
        license.setDescription(parameters.getDescription());

        // Устанавливаем даты начала и окончания только при первой создании лицензии
        if (licenseRepository.count() == 0) {
            license.setFirstActivationDate(new Date());
            license.setEndingDate(calculateExpirationDate(parameters.getDuration()));
        }

        // Генерируем уникальный код
        license.setCode(UUID.randomUUID().toString());

        // Сохраняем лицензию в репозитории
        return licenseRepository.save(license);
    }

    private Date calculateExpirationDate(Integer duration) {
        return new Date(System.currentTimeMillis() + duration * 24 * 60 * 60 * 1000L); // Длительность в днях
    }

    public License updateLicense(Long id, License updatedLicense) {
        License license = getLicenseById(id);
        license.setCode(updatedLicense.getCode());
        license.setOwner(updatedLicense.getOwner());
        license.setProduct(updatedLicense.getProduct());
        license.setType(updatedLicense.getType());
        license.setBlocked(updatedLicense.isBlocked());
        license.setDeviceCount(updatedLicense.getDeviceCount());
        license.setDuration(updatedLicense.getDuration());
        license.setDescription(updatedLicense.getDescription());
        return licenseRepository.save(license);
    }

    public void deleteLicense(Long id) {
        List<License> licenses = licenseRepository.findByProductId(id);
        if (!licenses.isEmpty()) {
            licenseRepository.deleteAll(licenses);
        }
        productRepository.deleteById(id);
    }

    public void deleteLicensesByProductId(Long productId) {
        licenseRepository.deleteByProductId(productId);
    }

    public List<License> getLicensesByProductId(Long productId) {
        return licenseRepository.findByProductId(productId);
    }

    public Optional<License> findLicenseByActivationCode(String activationCode) {
        return licenseRepository.findByCode(activationCode);
    }

    public void validateActivation(License license, Device device, ApplicationUser user) {
        if (license.isBlocked()) {
            throw new ActivationException("License is blocked");
        }
        if (license.getEndingDate() != null && license.getEndingDate().before(new Date())) {
            throw new ActivationException("License is expired");
        }
        // Дополнительные проверки здесь (если есть)
    }

    public void updateLicense(License license){
        license.setActivated(true);
        licenseRepository.save(license);
    }
}