CREATE TABLE atleta_squadra (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    atleta_id BIGINT NOT NULL REFERENCES atleta(id) ON DELETE CASCADE,
    squadra_id BIGINT NOT NULL REFERENCES squadra(id) ON DELETE CASCADE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- Indice su squadra_id (utile per ricerche/filtering per squadra)
CREATE INDEX idx_atleta_squadra_squadra_id ON atleta_squadra (squadra_id);

-- Opzionale: evitare duplicate associazioni atleta-squadra
ALTER TABLE atleta_squadra
  ADD CONSTRAINT uq_atleta_squadra UNIQUE (atleta_id, squadra_id);