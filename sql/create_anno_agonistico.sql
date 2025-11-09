-- Table: public.anno_agonistico

-- DROP TABLE IF EXISTS public.anno_agonistico;

CREATE TABLE IF NOT EXISTS public.anno_agonistico
(
    id bigint NOT NULL DEFAULT nextval('anno_agonistico_id_seq'::regclass),
    anno character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT anno_agonistico_pkey PRIMARY KEY (id),
    CONSTRAINT anno_agonistico_anno_key UNIQUE (anno)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.anno_agonistico
    OWNER to medical;