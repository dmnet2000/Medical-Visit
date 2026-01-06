-- Versione semplificata ma sicura
CREATE TABLE IF NOT EXISTS public.authentication
(
    id BIGSERIAL PRIMARY KEY,
    id_allenatore BIGINT NOT NULL UNIQUE,
    username VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    data_creazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_ultimo_accesso TIMESTAMP,
    attivo BOOLEAN DEFAULT TRUE,

    CONSTRAINT fk_allenatore
        FOREIGN KEY (id_allenatore)
        REFERENCES public.allenatore(id)
        ON DELETE CASCADE,

    -- Constraint per validare username
    CONSTRAINT chk_username_length CHECK (char_length(username) >= 3)
);

-- Indici
CREATE INDEX idx_auth_username ON public.authentication(username);

-- Permessi
ALTER TABLE IF EXISTS public.authentication OWNER TO medical;

-- Commenti per documentazione
COMMENT ON TABLE public.authentication IS 'Tabella per autenticazione allenatori';
COMMENT ON COLUMN public.authentication.password_hash IS 'Hash bcrypt della password (mai salvare in chiaro)';