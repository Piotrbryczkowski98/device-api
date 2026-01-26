package com.assessment.device.integration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.assessment.device.AbstractIntegrationTest;
import com.assessment.device.dto.Device;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @DisplayName("GET /device - Should return a page of devices")
    void findAll_ShouldReturnAllStoredDevices() {
        ResponseEntity<PageResponse<Device>> response = restTemplate.exchange(
                "/device",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Device> devices = response.getBody().getContent();
        assertThat(devices).isNotNull();
        assertThat(devices.size()).isEqualTo(7);
    }

    @Test
    @DisplayName("GET /device?state=IN_USE - Should return only devices with state IN_USE")
    void findAll_ShouldReturnOnlyInUseDevices() {
        ResponseEntity<PageResponse<Device>> response = restTemplate.exchange(
                "/device?state=IN_USE",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getContent().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("GET /device?page=0&size=2 - Should return paginated results")
    void findAll_ShouldReturnPaginatedResults() {
        // Test the new pagination functionality specifically
        ResponseEntity<PageResponse<Device>> response = restTemplate.exchange(
                "/device?page=0&size=2",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getContent().size()).isEqualTo(2);
        assertThat(response.getBody().getTotalElements()).isEqualTo(7);
    }
}

class PageResponse<T> {
    private List<T> content;
    private long totalElements;
    @JsonProperty("content") public List<T> getContent() { return content; }
    @JsonProperty("totalElements") public long getTotalElements() { return totalElements; }
}
