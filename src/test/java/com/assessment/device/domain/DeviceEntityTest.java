package com.assessment.device.domain;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.assessment.device.domain.exception.IllegalDeviceStateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DeviceEntityTest {

    @Test
    @DisplayName("Should throw exception when updating name of IN_USE device")
    void validateUpdate_InUse_ThrowsException() {
        var device = DeviceEntity.builder()
                .name("Old Name")
                .state(DeviceState.IN_USE)
                .build();

        assertThrows(IllegalDeviceStateException.class, () ->
                device.validateUpdate("New Name", null)
        );
    }

    @Test
    @DisplayName("Should allow state update even if IN_USE")
    void validateUpdate_InUse_AllowsStateChange() {
        var device = DeviceEntity.builder()
                .name("Same Name")
                .state(DeviceState.IN_USE)
                .build();

        assertDoesNotThrow(() -> device.validateUpdate("Same Name", null));
    }

    @Test
    @DisplayName("Should allow name and brand updates when device is NOT in use")
    void validateUpdate_NotInUse_AllowsAllChanges() {
        var device = DeviceEntity.builder()
                .name("Old Name")
                .brand("Old Brand")
                .state(DeviceState.INACTIVE)
                .build();

        assertDoesNotThrow(() ->
                device.validateUpdate("New Name", "New Brand")
        );
    }
}