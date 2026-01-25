package com.assessment.device.api.request;

import com.assessment.device.domain.DeviceState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateDeviceRequest(
        @NotBlank String name,
        @NotBlank String brand,
        @NotNull DeviceState state
) {}
