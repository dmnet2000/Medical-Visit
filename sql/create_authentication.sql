-- Tabella authentication (GENERICA per tutti i tipi utente)
CREATE TABLE IF NOT EXISTS public.authentication (
    id BIGSERIAL PRIMARY KEY,
    id_user BIGINT,  -- Può riferirsi a allenatore, atleta, dirigente
    username VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    cod_utente VARCHAR(50) NOT NULL,  -- Enum: ATLETA, ALLENATORE, DIRIGENTE, ADMIN
    data_creazione TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_ultimo_accesso TIMESTAMP,
    attivo BOOLEAN DEFAULT TRUE,

    -- Constraint per validare username
    CONSTRAINT chk_username_length CHECK (char_length(username) >= 3),

    -- Constraint per validare cod_utente
    CONSTRAINT chk_cod_utente CHECK (cod_utente IN ('ATLETA', 'ALLENATORE', 'DIRIGENTE', 'ADMIN'))
);

-- Indici
CREATE INDEX idx_auth_username ON public.authentication(username);

-- Permessi
ALTER TABLE IF EXISTS public.authentication OWNER TO medical;

-- Commenti per documentazione
COMMENT ON TABLE public.authentication IS 'Tabella per autenticazione allenatori';
COMMENT ON COLUMN public.authentication.password_hash IS 'Hash bcrypt della password (mai salvare in chiaro)';