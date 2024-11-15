package mtuci.rbpomtuci2024.Repository;

import mtuci.rbpomtuci2024.model.LicenseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseTypeRepository extends JpaRepository<LicenseType, Long> {

}

