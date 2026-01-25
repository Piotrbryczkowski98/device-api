package com.assessment.device.api;

import com.assessment.device.api.request.CreateDeviceRequest;
import com.assessment.device.api.request.UpdateDeviceRequest;
import com.assessment.device.domain.DeviceState;
import com.assessment.device.dto.Device;
import com.assessment.device.service.DeviceService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/device")
@AllArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping
    public List<Device> findAllDevices(@RequestParam(required = false) String brand, @RequestParam(required = false) DeviceState state) {
        return deviceService.findDevices(brand, state);
    }

    @PostMapping
    public Device createNewDevice(@RequestBody @Valid CreateDeviceRequest request) {
        return deviceService.createDevice(
                request.name(),
                request.brand(),
                request.state()
        );
    }

    @PatchMapping("/{id}")
    public Device updateDevice(
            @PathVariable UUID id,
            @RequestBody UpdateDeviceRequest request
    ) {
        if (request.name().isEmpty() && request.brand().isEmpty() && request.state().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least one parameter must be provided");
        }

        return deviceService.updateDevice(
                id,
                request.name().orElse(null),
                request.brand().orElse(null),
                request.state().orElse(null)
        );
    }

    @GetMapping("/{id}")
    public Device findDeviceById(@PathVariable UUID id) {
        return deviceService.findDeviceById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDeviceById(@PathVariable UUID id) {
        deviceService.deleteDeviceById(id);
    }
}



