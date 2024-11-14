package Repository;

import model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

}
