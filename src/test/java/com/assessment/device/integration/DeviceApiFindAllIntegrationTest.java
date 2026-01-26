package com.assessment.device.integration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.assessment.device.AbstractIntegrationTest;
import com.assessment.device.dto.Device;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

@SqlGroup({
        @Sql(
                scripts = {"/sql/input/insert_multiple_devices.sql"},
                executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS
        ),
        @Sql(
                scripts = "/sql/input/cleanup.sql",
                executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS
        )
})
public class DeviceApiFindAllIntegrationTest extends AbstractIntegrationTest {


    @Test
    @DisplayName("GET /device - Should return all devices when no filters are provided")
    void findAll_ShouldReturnAllStoredDevices() {
        ResponseEntity<List<Device>> response = restTemplate.exchange(
                "/device",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Device> devices = response.getBody();
        assertThat(devices).isNotNull();
        assertThat(devices.size()).isEqualTo(7);
    }

    @Test
    @DisplayName("GET /device?state=IN_USE - Should return only devices with state IN_USE")
    void findAll_ShouldReturnOnlyInUseDevices() {
        ResponseEntity<List<Device>> response = restTemplate.exchange(
                "/device?state=IN_USE",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Device> devices = response.getBody();
        assertThat(devices).isNotNull();
        assertThat(devices.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("GET /device?state=IN_USE - Should return only devices with brand Apple")
    void findAll_ShouldReturnBrandAppleDevices() {
        ResponseEntity<List<Device>> response = restTemplate.exchange(
                "/device?brand=Apple",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Device> devices = response.getBody();
        assertThat(devices).isNotNull();
        assertThat(devices.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("GET /device?brand=ApPlE - Should return only devices with brand Apple and ignore case")
    void findAll_ShouldReturnBrandAppleDevicesIgnoreCase() {
        ResponseEntity<List<Device>> response = restTemplate.exchange(
                "/device?brand=ApPlE",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Device> devices = response.getBody();
        assertThat(devices).isNotNull();
        assertThat(devices.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("GET /device?brand=Apple&state=AVAILABLE - Should return only devices with brand Apple and state AVAILABLE")
    void findAll_ShouldReturnBrandAppleDevicesAndStateInactive() {
        ResponseEntity<List<Device>> response = restTemplate.exchange(
                "/device?brand=Apple&state=AVAILABLE",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Device> devices = response.getBody();
        assertThat(devices).isNotNull();
        assertThat(devices.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("GET /device?brand=NotExisting - Should return empty list for not existing brand")
    void findAll_ShouldReturnEmptyList() {
        ResponseEntity<List<Device>> response = restTemplate.exchange(
                "/device?brand=NotExisting",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Device> devices = response.getBody();
        assertThat(devices).isNotNull();
        assertThat(devices.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("GET /state?state=NOT_EXISTING - Should return BAD REQUEST for not existing status")
    void findAll_ShouldReturnBadRequestForNotExistingState() {
        ResponseEntity<ProblemDetail> response = restTemplate.exchange(
                "/device?state=NOT_EXISTING",
                HttpMethod.GET,
                null,
                ProblemDetail.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
