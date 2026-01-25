package com.assessment.device_api;

import com.assessment.device_api.model.DeviceEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<DeviceEntity, UUID> {
}