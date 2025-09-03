CREATE TABLE atleta (
  id SERIAL PRIMARY KEY,
  cognome VARCHAR(50) NOT NULL,
  nome VARCHAR(50) NOT NULL,
  codice_fiscale VARCHAR(16) UNIQUE NOT NULL,
  data_di_nascita DATE NOT NULL,
  data_scadenza_visita_medica DATE NOT NULL
);