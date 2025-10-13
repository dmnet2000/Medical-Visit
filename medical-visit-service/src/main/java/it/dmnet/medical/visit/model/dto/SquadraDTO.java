package it.dmnet.medical.visit.model.dto;

public class SquadraDTO {
    long id;
    String nomeSquadra;
    String nomeAllenatore;
    String annoAgonistico;

    public String getNomeSquadra() {
        return nomeSquadra;
    }

    public void setNomeSquadra(String nomeSquadra) {
        this.nomeSquadra = nomeSquadra;
    }

    public String getNomeAllenatore() {
        return nomeAllenatore;
    }

    public void setNomeAllenatore(String nomeAllenatore) {
        this.nomeAllenatore = nomeAllenatore;
    }

    public String getAnnoAgonistico() {
        return annoAgonistico;
    }

    public void setAnnoAgonistico(String annoAgonistico) {
        this.annoAgonistico = annoAgonistico;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SquadraDTO{" +
                "nomeSquadra='" + nomeSquadra + '\'' +
                ", nomeAllenatore='" + nomeAllenatore + '\'' +
                ", annoAgonistico='" + annoAgonistico + '\'' +
                '}';
    }
}
