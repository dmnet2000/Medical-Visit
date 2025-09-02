package it.dmnet.medical.visit.model.dto;



import java.time.LocalDate;


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

    public Atleta(){

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

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public LocalDate getDataScadenzaVisitaMedica() {
        return dataScadenzaVisitaMedica;
    }

    public void setDataScadenzaVisitaMedica(LocalDate dataScadenzaVisitaMedica) {
        this.dataScadenzaVisitaMedica = dataScadenzaVisitaMedica;
    }

    @Override
    public String toString() {
        return "Atleta{" +
                "cognome='" + cognome + '\'' +
                ", nome='" + nome + '\'' +
                ", codiceFiscale='" + codiceFiscale + '\'' +
                ", dataNascita=" + dataNascita +
                ", dataScadenzaVisitaMedica=" + dataScadenzaVisitaMedica +
                '}';
    }
}


