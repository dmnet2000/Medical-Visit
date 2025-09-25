package it.dmnet.medical.visit.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "allenatore")
public class AllenatoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, length = 50)
    public String nome;

    @Column(nullable = false, length = 50)
    public String cognome;

    @Column(length = 100)
    public String indirizzo;

    @Column(name = "indirizzo_mail", length = 100)
    public String indirizzoMail;

    @Column(name = "numero_telefono", length = 20)
    public String numeroTelefono;

    @Column(name = "codice_fiscale", length = 16)
    public String codiceFiscale;
}