package it.dmnet.medical.visit.model.enumeration;

public enum TipoUtente {
    ATLETA("Atleta"),
    ALLENATORE("Allenatore"),
    DIRIGENTE("Dirigente"),
    ADMIN("Amministratore");

    private final String descrizione;

    TipoUtente(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getCodice() {
        return this.name();
    }
}