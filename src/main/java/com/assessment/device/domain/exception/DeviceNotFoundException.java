package com.assessment.device.domain.exception;

import java.util.UUID;

public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException(UUID id) {
        super("Device with id " + id + " not found");
    }
}