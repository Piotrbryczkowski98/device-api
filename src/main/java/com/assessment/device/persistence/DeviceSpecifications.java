package com.assessment.device.persistence;

import com.assessment.device.domain.DeviceEntity;
import com.assessment.device.domain.DeviceState;
import org.springframework.data.jpa.domain.Specification;
public final class DeviceSpecifications {

    private DeviceSpecifications() {}

    public static Specification<DeviceEntity> hasBrand(String brand) {
        return (root, query, cb) -> cb.equal(
                cb.lower(root.get("brand")),
                brand.toLowerCase()
        );
    }
    public static Specification<DeviceEntity> hasState(DeviceState state) {
        return (root, query, cb) -> cb.equal(root.get("state"), state);
    }

    public static Specification<DeviceEntity> buildSpec(String brand, DeviceState state) {
        Specification<DeviceEntity> spec = Specification.where((root, query, cb) -> cb.conjunction());
        if (brand != null) {
            spec = spec.and(hasBrand(brand));
        }
        if (state != null) {
            spec = spec.and(hasState(state));
        }
        return spec;
    }
}