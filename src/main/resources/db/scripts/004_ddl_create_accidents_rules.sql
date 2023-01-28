CREATE TABLE IF NOT EXISTS accidents_rules
(
    id          SERIAL PRIMARY KEY,
    accident_id INT NOT NULL REFERENCES accident (id),
    rule_id     INT NOT NULL REFERENCES auto_rules (id)
);
