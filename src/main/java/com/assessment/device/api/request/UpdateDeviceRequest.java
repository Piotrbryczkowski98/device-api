package com.assessment.device.api.request;

import com.assessment.device.domain.DeviceState;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdateDeviceRequest(
        Optional<String> name,
        Optional<String> brand,
        Optional<DeviceState> state
) {}