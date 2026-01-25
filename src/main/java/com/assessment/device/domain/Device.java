package com.assessment.device.domain;

import java.time.Instant;
import java.util.UUID;

public record Device(
        UUID id,
        String name,
        String brand,
        DeviceState state,
        Instant creationTime
) {}