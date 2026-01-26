package com.assessment.device.integration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.assessment.device.AbstractIntegrationTest;
import com.assessment.device.dto.Device;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

public class DeviceApiFindByIdIntegrationTest extends AbstractIntegrationTest {

    @Test
    @SqlGroup({
            @Sql(
                    scripts = {"/sql/input/insert_single_in_use_device.sql"},
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
            ),
            @Sql(
                    scripts = "/sql/input/cleanup.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
            )
    })
    @DisplayName("GET /device/{id} - Should return a device when the ID exists in the database")
    void getDeviceById_Success() {
        var deviceId = "550e8400-e29b-41d4-a716-446655440000";

        ResponseEntity<Device> response = restTemplate.exchange(
                "/device/" + deviceId,
                HttpMethod.GET,
                null,
                Device.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Device device = response.getBody();
        assertThat(device)
                .isNotNull()
                .extracting(Device::name, Device::brand)
                .containsExactly("Test Device", "TestBrand");
    }

    @Test
    @DisplayName("GET /device/{id} - Should return 404 and error details when ID is not found")
    void getDeviceById_NotFound() {
        var nonExistentId = UUID.randomUUID();

        ResponseEntity<ProblemDetail> response = restTemplate.exchange(
                "/device/" + nonExistentId,
                HttpMethod.GET,
                null,
                ProblemDetail.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().getDetail()).contains(nonExistentId.toString());
    }

    @Test
    @DisplayName("GET /device/{id} - Should return 400 when provided ID is not an UUID")
    void getDeviceById_NotUUID_BadRequest() {
        var nonExistentId = "randomNotUUID";

        ResponseEntity<ProblemDetail> response = restTemplate.exchange(
                "/device/" + nonExistentId,
                HttpMethod.GET,
                null,
                ProblemDetail.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
