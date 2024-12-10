package ru.mtuci.rbpomtuci2024.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mtuci.rbpomtuci2024.Repository.DeviceRepository;
import ru.mtuci.rbpomtuci2024.model.ApplicationUser;
import ru.mtuci.rbpomtuci2024.model.Device;

import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public Device registerOrUpdateDevice(String deviceId, ApplicationUser user) {

        Optional<Device> optionalDevice = deviceRepository.findByMacAddress(deviceId);
        Device device;

        if (optionalDevice.isPresent()) {
            device = optionalDevice.get();
        } else {
            device = new Device();
            device.setMacAddress(deviceId);
        }

        device.setUser(user);

        return deviceRepository.save(device);
    }
}
