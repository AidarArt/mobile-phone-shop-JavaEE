CREATE TABLE IF NOT EXISTS phones (
    id BIGSERIAL primary key,
    model VARCHAR(255) NOT NULL,
    manufacturer VARCHAR(255) NOT NULL,
    production_date DATE NOT NULL,
    serial_number uuid NOT NULL UNIQUE,
    color VARCHAR(255) NOT NULL
);