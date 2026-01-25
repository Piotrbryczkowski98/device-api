package com.assessment.device.service;

import com.assessment.device.domain.DeviceEntity;
import com.assessment.device.domain.DeviceState;
import com.assessment.device.persistence.DeviceRepository;
import com.assessment.device.persistence.DeviceSpecifications;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public List<DeviceEntity> findDevices(String brand, DeviceState state) {
        Specification<DeviceEntity> spec = DeviceSpecifications.buildSpec(brand, state);
        return deviceRepository.findAll(spec);
    }
}
