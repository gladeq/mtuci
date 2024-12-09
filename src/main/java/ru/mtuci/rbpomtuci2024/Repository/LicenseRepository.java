package ru.mtuci.rbpomtuci2024.Repository;

import org.springframework.data.repository.query.Param;
import ru.mtuci.rbpomtuci2024.model.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LicenseRepository extends JpaRepository<License, Long> {
    License findByUser_Id(Long userId);
    void deleteByProductId(Long productId);
    List<License> findByProductId(Long productId);
    List<License> findByTypeId(@Param("id") Long id);
    Optional<License> findByCode(String code);

}
