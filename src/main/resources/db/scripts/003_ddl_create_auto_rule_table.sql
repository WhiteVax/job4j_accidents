CREATE TABLE IF NOT EXISTS auto_rules
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR
);

COMMENT ON TABLE auto_rules IS 'ПДД.'