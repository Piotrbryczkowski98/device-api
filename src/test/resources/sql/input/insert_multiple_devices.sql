DELETE FROM device;

INSERT INTO device (id, name, brand, state, creation_time)
VALUES
    ('550e8400-e29b-41d4-a716-446655440001', 'iPhone 15 Pro', 'Apple', 'AVAILABLE', '2026-01-26T10:00:00Z'),
    ('550e8400-e29b-41d4-a716-446655440002', 'Galaxy S24 Ultra', 'Samsung', 'IN_USE', '2026-01-26T11:00:00Z'),
    ('550e8400-e29b-41d4-a716-446655440003', 'Pixel 8 Enterprise', 'Google', 'INACTIVE', '2026-01-26T12:00:00Z'),
    ('550e8400-e29b-41d4-a716-446655440004', 'Iphone 13 Pro Max', 'Apple', 'INACTIVE', '2026-01-26T13:00:00Z'),
    ('550e8400-e29b-41d4-a716-446655440005', 'Iphone 16', 'Apple', 'AVAILABLE', '2026-01-26T14:00:00Z'),
    ('550e8400-e29b-41d4-a716-446655440006', 'ThinkPad X1 Carbon', 'Lenovo', 'IN_USE', '2026-01-26T15:00:00Z'),
    ('550e8400-e29b-41d4-a716-446655440007', 'Surface Pro 9', 'Microsoft', 'INACTIVE', '2026-01-26T16:00:00Z');