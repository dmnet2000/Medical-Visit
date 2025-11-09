-- Table: public.squadra

-- DROP TABLE IF EXISTS public.squadra;

CREATE TABLE IF NOT EXISTS public.squadra
(
    id bigint NOT NULL DEFAULT nextval('atleta_id_seq'::regclass),
    id_allenatore bigint NOT NULL,
    id_anno_agonistico bigint NOT NULL,
    nome character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT squadra_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.squadra
    OWNER to medical;