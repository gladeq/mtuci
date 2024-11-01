package Repository;

import model.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseRepository extends JpaRepository<License, Long> {
    License findByDeviceId(String deviceId);

    static License findByUserIdAndDeviceId(Long userId, String deviceId) {
        return null;
    }
}
