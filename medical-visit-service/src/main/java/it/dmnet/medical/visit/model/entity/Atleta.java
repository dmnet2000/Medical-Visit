package it.dmnet.medical.visit.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="atleta")
public class Atleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String codiceFiscale;

    @Column(nullable = false)
    private LocalDate dataDiNascita;

    @Column(nullable = false)
    private LocalDate dataScadenzaVisitaMedica;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public LocalDate getDataScadenzaVisitaMedica() {
        return dataScadenzaVisitaMedica;
    }

    public void setDataScadenzaVisitaMedica(LocalDate dataScadenzaVisitaMedica) {
        this.dataScadenzaVisitaMedica = dataScadenzaVisitaMedica;
    }
}
