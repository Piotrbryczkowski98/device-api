package com.assessment.device.integration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.assessment.device.AbstractIntegrationTest;
import com.assessment.device.api.request.CreateDeviceRequest;
import com.assessment.device.domain.DeviceState;
import com.assessment.device.dto.Device;
import com.assessment.device.persistence.DeviceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

@Sql(
        scripts = "/sql/input/cleanup.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class DeviceApiCreateDeviceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private DeviceRepository deviceRepository;

    @Test
    @DisplayName("POST /device - Should create a new device and return it")
    void shouldCreateDevice_WhenRequestIsValid() {
        var request = new CreateDeviceRequest("Pixel 9", "Google", DeviceState.AVAILABLE);

        ResponseEntity<Device> response = restTemplate.postForEntity(
                "/device",
                request,
                Device.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Device createdDevice = response.getBody();
        assertThat(createdDevice).isNotNull();
        assertThat(createdDevice.id()).isNotNull();
        assertThat(createdDevice.name()).isEqualTo("Pixel 9");
        assertThat(createdDevice.brand()).isEqualTo("Google");
        assertThat(createdDevice.state()).isEqualTo(DeviceState.AVAILABLE);

        assertThat(deviceRepository.existsById(createdDevice.id())).isTrue();
    }

    @Test
    @DisplayName("POST /device - Should return 400 when name is blank")
    void shouldReturn400_WhenNameIsMissing() {
        var invalidRequest = new CreateDeviceRequest("", "Google", DeviceState.AVAILABLE);

        ResponseEntity<ProblemDetail> response = restTemplate.postForEntity(
                "/device",
                invalidRequest,
                ProblemDetail.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("POST /device - Should return 400 when brand is blank")
    void shouldReturn400_WhenBrandIsBlank() {
        var invalidRequest = new CreateDeviceRequest("Pixel 9", "", DeviceState.AVAILABLE);

        ResponseEntity<ProblemDetail> response = restTemplate.postForEntity(
                "/device",
                invalidRequest,
                ProblemDetail.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
