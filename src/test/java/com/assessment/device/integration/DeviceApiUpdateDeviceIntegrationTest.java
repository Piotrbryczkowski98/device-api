package com.assessment.device.integration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.assessment.device.AbstractIntegrationTest;
import com.assessment.device.api.request.UpdateDeviceRequest;
import com.assessment.device.domain.DeviceState;
import com.assessment.device.dto.Device;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

public class DeviceApiUpdateDeviceIntegrationTest extends AbstractIntegrationTest {

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
    @Test
    @DisplayName("PATCH /device/{id} - Should return 400 when updating name of IN_USE device")
    void shouldReturn400_WhenUpdatingNameOfInUseDevice() {
        UUID inUseId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        var updateRequest = new UpdateDeviceRequest(
                Optional.of("New Name"),
                Optional.empty(),
                Optional.empty()
        );

        HttpEntity<UpdateDeviceRequest> entity = new HttpEntity<>(updateRequest);

        ResponseEntity<ProblemDetail> response = restTemplate.exchange(
                "/device/" + inUseId,
                HttpMethod.PATCH,
                entity,
                ProblemDetail.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDetail())
                .contains("Cannot update name or brand while device is IN_USE");
    }

    @SqlGroup({
            @Sql(
                    scripts = {"/sql/input/insert_single_available_device.sql"},
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
            ),
            @Sql(
                    scripts = "/sql/input/cleanup.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
            )
    })
    @Test
    @DisplayName("PATCH /device/{id} - Should update name successfully when device is AVAILABLE")
    void shouldReturn200_WhenUpdatingNameOfAvailableDevice() {
        UUID availableId = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");

        var updateRequest = new UpdateDeviceRequest(
                Optional.of("New Name"),
                Optional.empty(),
                Optional.empty()
        );

        HttpEntity<UpdateDeviceRequest> entity = new HttpEntity<>(updateRequest);

        ResponseEntity<Device> response = restTemplate.exchange(
                "/device/" + availableId,
                HttpMethod.PATCH,
                entity,
                Device.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().name()).isEqualTo("New Name");
        assertThat(response.getBody().brand()).isEqualTo("TestBrand");
        assertThat(response.getBody().state()).isEqualTo(DeviceState.AVAILABLE);
    }


    //TODO: This is a case that should be validated with the business, the wanted behaviour
    // is not mentioned in the document so for sake of thiss assignment I assumed this is the correct behaviour
    @SqlGroup({
            @Sql(
                    scripts = {"/sql/input/insert_single_available_device.sql"},
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
            ),
            @Sql(
                    scripts = "/sql/input/cleanup.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
            )
    })
    @Test
    @DisplayName("PATCH /device/{id} - Should update name and state successfully when device is AVAILABLE")
    void shouldReturn200_WhenUpdatingNameAndStateOfAvailableDevice() {
        UUID availableId = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");

        var updateRequest = new UpdateDeviceRequest(
                Optional.of("New Name"),
                Optional.empty(),
                Optional.of(DeviceState.IN_USE)
        );

        HttpEntity<UpdateDeviceRequest> entity = new HttpEntity<>(updateRequest);

        ResponseEntity<Device> response = restTemplate.exchange(
                "/device/" + availableId,
                HttpMethod.PATCH,
                entity,
                Device.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().name()).isEqualTo("New Name");
        assertThat(response.getBody().brand()).isEqualTo("TestBrand");
        assertThat(response.getBody().state()).isEqualTo(DeviceState.IN_USE);
    }

    @SqlGroup({
            @Sql(
                    scripts = {"/sql/input/insert_single_available_device.sql"},
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
            ),
            @Sql(
                    scripts = "/sql/input/cleanup.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
            )
    })
    @Test
    @DisplayName("PATCH /device/{id} - Should update name, brand and state successfully when device is AVAILABLE")
    void shouldReturn200_WhenUpdatingNameBrandAndStateOfAvailableDevice() {
        UUID availableId = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");

        var updateRequest = new UpdateDeviceRequest(
                Optional.of("New Name"),
                Optional.of("New Brand"),
                Optional.of(DeviceState.INACTIVE)
        );

        HttpEntity<UpdateDeviceRequest> entity = new HttpEntity<>(updateRequest);

        ResponseEntity<Device> response = restTemplate.exchange(
                "/device/" + availableId,
                HttpMethod.PATCH,
                entity,
                Device.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().name()).isEqualTo("New Name");
        assertThat(response.getBody().brand()).isEqualTo("New Brand");
        assertThat(response.getBody().state()).isEqualTo(DeviceState.INACTIVE);
    }
}
