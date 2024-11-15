package mtuci.rbpomtuci2024.Repository;

import mtuci.rbpomtuci2024.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

}

