CREATE TABLE IF NOT EXISTS capacidades (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    tecnologia_ids TEXT[]
);