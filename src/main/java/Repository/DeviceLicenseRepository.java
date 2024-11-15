package Repository;

import model.DeviceLicense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceLicenseRepository extends JpaRepository<DeviceLicense, Long> {

}
