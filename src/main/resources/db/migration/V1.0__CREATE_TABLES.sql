CREATE SCHEMA IF NOT EXISTS transport;

CREATE TABLE IF NOT EXISTS transport.transport
(
    id   UUID PRIMARY KEY,
    body TEXT NOT NULL
);

CREATE SCHEMA IF NOT EXISTS cargo;

CREATE TABLE IF NOT EXISTS cargo.cargo
(
    id           UUID PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    form         TEXT         NOT NULL,
    height       INTEGER      NOT NULL,
    width        INTEGER      NOT NULL,
    area         INTEGER      NOT NULL,
    type         CHAR(1)      NOT NULL,
    transport_id UUID,
    FOREIGN KEY (transport_id) REFERENCES transport.transport (id)
);