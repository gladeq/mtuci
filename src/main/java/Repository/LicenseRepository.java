package Repository;

import model.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseRepository extends JpaRepository<License, Long> {
    License findByDeviceId(String deviceId);

    License findByUser_Id(Long userId);
}