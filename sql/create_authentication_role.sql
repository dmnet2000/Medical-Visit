-- Tabella authentication_role
CREATE TABLE authentication_role (
    id SERIAL PRIMARY KEY,
    id_authentication BIGINT NOT NULL,
    id_role BIGINT NOT NULL,
    enable BOOLEAN DEFAULT true,
    FOREIGN KEY (id_authentication) REFERENCES authentication(id) ON DELETE CASCADE,
    FOREIGN KEY (id_role) REFERENCES role(id) ON DELETE CASCADE,
    UNIQUE(id_authentication, id_role)
);