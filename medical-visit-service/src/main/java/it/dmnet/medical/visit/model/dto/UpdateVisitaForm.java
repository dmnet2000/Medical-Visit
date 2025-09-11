package it.dmnet.medical.visit.model.dto;

public class UpdateVisitaForm {
    private String codFiscale;
    private String newDate;

    public String getNewDate() {
        return newDate;
    }

    public void setNewDate(String newDate) {
        this.newDate = newDate;
    }

    public String getCodFiscale() {
        return codFiscale;
    }

    public void setCodFiscale(String codFiscale) {
        this.codFiscale = codFiscale;
    }

    public UpdateVisitaForm(String codFiscale, String newDate) {
        this.codFiscale = codFiscale;
        this.newDate = newDate;
    }

    public UpdateVisitaForm() {
    }
}
