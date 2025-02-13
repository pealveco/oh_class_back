-- CREATE TABLE IF NOT EXISTS tecnologias (
--     id BIGSERIAL PRIMARY KEY,
--     name VARCHAR(255),
--     description TEXT
-- );

-- CREATE TABLE IF NOT EXISTS capacidades (
--     id BIGSERIAL PRIMARY KEY,
--     name VARCHAR(255),
--     description TEXT,
--     tecnologia_ids TEXT[]
-- );

CREATE TABLE IF NOT EXISTS bootcamps (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    capacidad_ids BIGINT[] NOT NULL
);