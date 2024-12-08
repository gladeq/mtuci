package ru.mtuci.rbpomtuci2024.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ru.mtuci.rbpomtuci2024.model.*;
import ru.mtuci.rbpomtuci2024.service.impl.LicenseServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/licenses")
@RequiredArgsConstructor
public class LicenseController {

    private final LicenseServiceImpl licenseService;

    @GetMapping
    public ResponseEntity<List<LicenseResponseDTO>> getAllLicenses() {
        List<License> licenses = licenseService.getAllLicenses();
        List<LicenseResponseDTO> licenseDTOs = licenses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(licenseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LicenseResponseDTO> getLicenseById(@PathVariable Long id) {
        License license = licenseService.getLicenseById(id);
        LicenseResponseDTO licenseDTO = convertToDTO(license);
        return ResponseEntity.ok(licenseDTO);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LicenseResponseDTO> createLicense(@RequestBody LicenseRequest licenseRequest) {
        Long productId = licenseRequest.getProductId();
        Long ownerId = licenseRequest.getOwnerId();
        Long licenseTypeId = licenseRequest.getLicenseTypeId();
        LicenseParameters parameters = licenseRequest.getParameters();

        License license = licenseService.createLicense(productId, ownerId, licenseTypeId, parameters);
        LicenseResponseDTO licenseDTO = convertToDTO(license);
        return ResponseEntity.status(HttpStatus.CREATED).body(licenseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LicenseResponseDTO> updateLicense(@PathVariable Long id, @RequestBody License license) {
        License updatedLicense = licenseService.updateLicense(id, license);
        LicenseResponseDTO licenseDTO = convertToDTO(updatedLicense);
        return ResponseEntity.ok(licenseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLicense(@PathVariable Long id) {
        licenseService.deleteLicense(id);
        return ResponseEntity.noContent().build();
    }

    private LicenseResponseDTO convertToDTO(License license) {
        LicenseResponseDTO licenseDTO = new LicenseResponseDTO();
        licenseDTO.setId(license.getId());
        licenseDTO.setCode(license.getCode());
        licenseDTO.setOwner(convertToUserDTO(license.getOwner()));
        licenseDTO.setProduct(convertToProductDTO(license.getProduct()));
        licenseDTO.setLicenseType(convertToLicenseTypeDTO(license.getType()));
        licenseDTO.setDuration(license.getDuration());
        licenseDTO.setDescription(license.getDescription());
        return licenseDTO;
    }

    private UserResponseDTO convertToUserDTO(ApplicationUser user) {
        UserResponseDTO userDTO = new UserResponseDTO();
        userDTO.setId(user.getId());
        userDTO.setLogin(user.getLogin());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    private ProductResponseDTO convertToProductDTO(Product product) {
        ProductResponseDTO productDTO = new ProductResponseDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        return productDTO;
    }

    private LicenseTypeResponseDTO convertToLicenseTypeDTO(LicenseType licenseType) {
        LicenseTypeResponseDTO licenseTypeDTO = new LicenseTypeResponseDTO();
        licenseTypeDTO.setId(licenseType.getId());
        licenseTypeDTO.setName(licenseType.getName());
        return licenseTypeDTO;
    }
}