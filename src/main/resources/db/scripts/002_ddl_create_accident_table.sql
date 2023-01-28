CREATE TABLE IF NOT EXISTS accident
(
    id          SERIAL PRIMARY KEY,
    name        TEXT NOT NULL,
    description TEXT,
    address     VARCHAR DEFAULT 'unknown',
    created     TIMESTAMP WITHOUT TIME ZONE,
    type_id     INT REFERENCES accident_type(id)
);

COMMENT ON TABLE accident IS 'Автомобильный инциденты.'