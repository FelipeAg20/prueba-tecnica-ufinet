-- Script de referencia — las tablas se crean automáticamente al levantar el backend.
-- Solo ejecuta este script si prefieres crear las tablas manualmente:
-- psql -U postgres -d autos_db -f schema.sql

CREATE TABLE IF NOT EXISTS users (
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    email    VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS cars (
    id           BIGSERIAL PRIMARY KEY,
    brand        VARCHAR(100) NOT NULL,
    model        VARCHAR(100) NOT NULL,
    year         INT          NOT NULL,
    plate_number VARCHAR(20)  NOT NULL UNIQUE,
    color        VARCHAR(50)  NOT NULL,
    user_id      BIGINT       NOT NULL,
    CONSTRAINT fk_cars_user FOREIGN KEY (user_id) REFERENCES users(id)
);
