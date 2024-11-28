package ru.mtuci.rbpomtuci2024.Repository;

import ru.mtuci.rbpomtuci2024.model.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseRepository extends JpaRepository<License, Long> {
    License findByUser_Id(Long userId);
}
