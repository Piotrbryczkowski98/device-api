package com.assessment.device.domain;

import jakarta.persistence.*;
import lombok.*;
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
}