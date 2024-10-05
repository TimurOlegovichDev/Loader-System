CREATE SCHEMA IF NOT EXISTS cargo;

CREATE TABLE IF NOT EXISTS cargo.cargo
(
    id
           SERIAL
        PRIMARY
            KEY,
    name
           VARCHAR(255) NOT NULL,
    form   TEXT         NOT NULL,
    height INTEGER      NOT NULL,
    width  INTEGER      NOT NULL,
    area   INTEGER      NOT NULL,
    type   CHAR(1)      NOT NULL
);

CREATE SCHEMA IF NOT EXISTS transport;

CREATE TABLE IF NOT EXISTS transport.transport
(
    id
        UUID
        PRIMARY
            KEY,
    body
        TEXT
        NOT
            NULL
);

CREATE TABLE IF NOT EXISTS transport.cargo_transport
(
    transport_id
        UUID
        NOT
            NULL,
    cargo_id
        INTEGER
        NOT
            NULL,
    PRIMARY
        KEY
        (
         transport_id,
         cargo_id
            ),
    FOREIGN KEY
        (
         transport_id
            ) REFERENCES transport.transport
        (
         id
            ),
    FOREIGN KEY
        (
         cargo_id
            ) REFERENCES cargo.cargo
        (
         id
            )
);