package Repository;

import model.DeviceLicense;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface DeviceLicenseRepository extends JpaRepository<DeviceLicense, Long> {}
