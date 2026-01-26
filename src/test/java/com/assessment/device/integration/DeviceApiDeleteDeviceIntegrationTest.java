package com.assessment.device.integration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.assessment.device.AbstractIntegrationTest;
import com.assessment.device.persistence.DeviceRepository;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

public class DeviceApiDeleteDeviceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private DeviceRepository deviceRepository;

    @Sql(
            scripts = {"/sql/input/insert_single_available_device.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Test
    @DisplayName("DELETE /device/{id} - Should return 204 and remove device from DB")
    void shouldDeleteExistingDevice() {
        UUID idToDelete = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");

        ResponseEntity<Void> response = restTemplate.exchange(
                "/device/" + idToDelete,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        boolean exists = deviceRepository.existsById(idToDelete);
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("DELETE /device/{id} - Should return 404 when ID does not exist")
    void shouldReturn404_WhenDeletingNonExistentId() {
        UUID randomId = UUID.randomUUID();

        ResponseEntity<ProblemDetail> response = restTemplate.exchange(
                "/device/" + randomId,
                HttpMethod.DELETE,
                null,
                ProblemDetail.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
