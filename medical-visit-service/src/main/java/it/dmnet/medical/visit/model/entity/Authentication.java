package it.dmnet.medical.visit.model.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "authentication", schema = "public")
public class Authentication extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType. IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "id_allenatore", nullable = false, unique = true)
    public Long idAllenatore;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    public String username;

    @Column(name = "password_hash", length = 255, nullable = false)
    public String passwordHash;

    @Column(name = "data_creazione", nullable = false, updatable = false)
    public LocalDateTime dataCreazione;

    @Column(name = "data_ultimo_accesso")
    public LocalDateTime dataUltimoAccesso;

    @Column(name = "attivo", nullable = false)
    public Boolean attivo = true;

    // Relazione con Allenatore (opzionale)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_allenatore", insertable = false, updatable = false)
    public AllenatoreEntity allenatore;

    @PrePersist
    protected void onCreate() {
        if (dataCreazione == null) {
            dataCreazione = LocalDateTime.now();
        }
        if (attivo == null) {
            attivo = true;
        }
    }

    // Metodi di utilità Panache
    public static Authentication findByUsername(String username) {
        return find("username", username).firstResult();
    }

    public static Authentication findByIdAllenatore(Long idAllenatore) {
        return find("idAllenatore", idAllenatore).firstResult();
    }

    public static List<Authentication> findAllActive() {
        return list("attivo", true);
    }

    public void updateLastAccess() {
        this.dataUltimoAccesso = LocalDateTime.now();
    }
}