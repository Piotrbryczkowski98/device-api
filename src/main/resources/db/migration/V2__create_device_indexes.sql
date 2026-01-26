CREATE INDEX idx_device_creation_time ON device (creation_time DESC);
CREATE INDEX idx_device_brand_state ON device (brand, state);