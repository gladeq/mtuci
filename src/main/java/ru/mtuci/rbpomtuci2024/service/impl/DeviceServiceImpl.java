package ru.mtuci.rbpomtuci2024.service.impl;

import ru.mtuci.rbpomtuci2024.Repository.DeviceRepository;
import ru.mtuci.rbpomtuci2024.model.Device;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl {

    private final DeviceRepository deviceRepository;

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public Device getDeviceById(Long id) {
        return deviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Device not found with id: " + id));
    }

    public Device createDevice(Device device) {
        return deviceRepository.save(device);
    }

    public Device updateDevice(Long id, Device updatedDevice) {
        Device device = getDeviceById(id);
        device.setName(updatedDevice.getName());
        device.setMacAddress(updatedDevice.getMacAddress());
        device.setUser(updatedDevice.getUser());
        return deviceRepository.save(device);
    }

    public void deleteDevice(Long id) {
        deviceRepository.deleteById(id);
    }
}

