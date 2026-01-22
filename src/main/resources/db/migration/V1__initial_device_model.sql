CREATE TYPE device_state AS ENUM ('AVAILABLE', 'IN_USE', 'INACTIVE');

CREATE TABLE device (
    id            UUID PRIMARY KEY,
    name          VARCHAR(255)                                       NOT NULL,
    brand         VARCHAR(255)                                       NOT NULL,
    state         device_state                                       NOT NULL,
    creation_time TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);