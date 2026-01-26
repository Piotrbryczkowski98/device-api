package com.assessment.device.service;

import com.assessment.device.domain.exception.IllegalDeviceStateException;
import com.assessment.device.dto.Device;
import com.assessment.device.domain.DeviceEntity;
import com.assessment.device.domain.DeviceState;
import com.assessment.device.domain.exception.DeviceNotFoundException;
import com.assessment.device.persistence.DeviceRepository;
import com.assessment.device.persistence.DeviceSpecifications;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public Page<Device> findDevices(String brand, DeviceState state, Pageable pageable) {
        Specification<DeviceEntity> spec = DeviceSpecifications.buildSpec(brand, state);

        return deviceRepository
                .findAll(spec, pageable)
                .map(DeviceEntity::toDTO);
    }

    public Device createDevice(String name, String brand, DeviceState state) {
        var device = DeviceEntity
                .builder()
                .name(name)
                .brand(brand)
                .state(state)
                .build();
        return deviceRepository
                .save(device)
                .toDTO();
    }

    @Transactional
    public Device updateDevice(UUID id, String name, String brand, DeviceState newState) {
        var device = deviceRepository.findById(id).orElseThrow();
        device.validateUpdate(name, brand);
        Optional.ofNullable(name).ifPresent(device::setName);
        Optional.ofNullable(brand).ifPresent(device::setBrand);
        Optional.ofNullable(newState).ifPresent(device::setState);
        return deviceRepository
                .save(device)
                .toDTO();
    }

    public Device findDeviceById(UUID id) {
        return deviceRepository
                .findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(id))
                .toDTO();
    }

    @Transactional
    public void deleteDeviceById(UUID id) {
        var device = deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(id));

        if(device.getState() == DeviceState.IN_USE) {
            throw new IllegalDeviceStateException("Device with state IN_USE cannot be deleted");
        }
        deviceRepository.delete(device);
    }
}
