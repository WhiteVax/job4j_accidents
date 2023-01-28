CREATE TABLE IF NOT EXISTS accident_type
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR
);

COMMENT ON TABLE accident_type IS 'Тип инцидентов.'