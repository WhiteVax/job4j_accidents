CREATE TABLE authorities
(
    id        SERIAL PRIMARY KEY,
    authority VARCHAR(50) NOT NULL UNIQUE
);

COMMENT ON TABLE authorities IS 'Роли для пользователей.'