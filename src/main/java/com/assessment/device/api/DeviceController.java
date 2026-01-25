package com.assessment.device.api;

import com.assessment.device.domain.DeviceState;
import com.assessment.device.persistence.DeviceRepository;
import com.assessment.device.domain.DeviceEntity;
import com.assessment.device.service.DeviceService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device")
@AllArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping("/devices")
    public List<DeviceEntity> findAllDevices(@RequestParam(required = false) String brand, @RequestParam(required = false) DeviceState state) {
        return deviceService.findDevices(brand, state);
    }
}



