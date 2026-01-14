-- Tabella role
CREATE TABLE role (
    id SERIAL PRIMARY KEY,
    cod_role VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(255)
);

