-- Table: public.allenatore

-- DROP TABLE IF EXISTS public.allenatore;

CREATE TABLE IF NOT EXISTS public.allenatore
(
    id bigint NOT NULL DEFAULT nextval('allenatore_id_seq'::regclass),
    nome character varying(50) COLLATE pg_catalog."default" NOT NULL,
    cognome character varying(50) COLLATE pg_catalog."default" NOT NULL,
    indirizzo character varying(100) COLLATE pg_catalog."default",
    indirizzo_mail character varying(100) COLLATE pg_catalog."default",
    numero_telefono character varying(20) COLLATE pg_catalog."default",
    codice_fiscale character varying(16) COLLATE pg_catalog."default",
    CONSTRAINT allenatore_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.allenatore
    OWNER to medical;