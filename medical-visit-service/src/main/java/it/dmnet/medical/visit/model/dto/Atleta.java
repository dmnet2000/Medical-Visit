package it.dmnet.medical.visit.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Atleta {
    public String cognome;
    public String nome;
    public String codiceFiscale;
    public LocalDate dataNascita;
    public LocalDate dataScadenzaVisitaMedica;

    public Atleta(String cognome, String nome, String codiceFiscale, LocalDate dataNascita, LocalDate dataScadenzaVisitaMedica) {
        this.cognome = cognome;
        this.nome = nome;
        this.codiceFiscale = codiceFiscale;
        this.dataNascita = dataNascita;
        this.dataScadenzaVisitaMedica = dataScadenzaVisitaMedica;
    }
}


