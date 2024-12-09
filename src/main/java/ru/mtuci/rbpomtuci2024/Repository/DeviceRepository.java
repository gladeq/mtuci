package ru.mtuci.rbpomtuci2024.Repository;

import ru.mtuci.rbpomtuci2024.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findByMacAddress(String macAddress);

}

