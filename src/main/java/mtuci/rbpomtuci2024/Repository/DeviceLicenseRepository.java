package mtuci.rbpomtuci2024.Repository;

import mtuci.rbpomtuci2024.model.DeviceLicense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceLicenseRepository extends JpaRepository<DeviceLicense, Long> {

}

