package com.assessment.device.domain;

import com.assessment.device.domain.exception.IllegalDeviceStateException;
import com.assessment.device.dto.Device;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SourceType;

import java.time.Instant;
import java.util.UUID;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "device")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class DeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "state", nullable = false)
    private DeviceState state;

    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "creation_time", nullable = false, updatable = false)
    private Instant creationTime;

    //TODO: Add a note in readme later
    public void validateUpdate(String newName, String newBrand) {
        if (this.state == DeviceState.IN_USE) {
            boolean nameChanged = newName != null && !newName.equals(this.name);
            boolean brandChanged = newBrand != null && !newBrand.equals(this.brand);

            if (nameChanged || brandChanged) {
                throw new IllegalDeviceStateException("Cannot update name or brand while device is IN_USE. Update the state first and try again.");
            }
        }
    }

    public Device toDTO() {
        return new Device(
                this.getId(),
                this.getName(),
                this.getBrand(),
                this.getState(),
                this.getCreationTime()
        );
    }
}