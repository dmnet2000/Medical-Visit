package it.dmnet.medical.visit.model.dto;

public class AtletaForm {
    private String cognome;
    private String nome;
    private String codiceFiscale;

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

    @Override
    public String toString() {
        return "AtletaResources{" +
                "cognome='" + cognome + '\'' +
                ", nome='" + nome + '\'' +
                ", dataNascita='" + codiceFiscale + '\'' +
                '}';
    }
}
