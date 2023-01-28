CREATE TABLE users
(
    id           SERIAL PRIMARY KEY,
    username     VARCHAR(50)  NOT NULL unique,
    password     VARCHAR(100) NOT NULL,
    enabled      boolean DEFAULT TRUE,
    authority_id INT NOT NULL REFERENCES authorities (id)
);

COMMENT ON TABLE users IS 'Таблица пользователей.'