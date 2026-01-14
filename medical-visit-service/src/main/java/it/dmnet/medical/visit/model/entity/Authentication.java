package it.dmnet.medical. visit.model.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import it.dmnet.medical.visit.model.enumeration.TipoUtente;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "authentication", schema = "public")
public class Authentication extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType. IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "id_user")
    public Long idUser;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    public String username;

    @Column(name = "password_hash", length = 255, nullable = false)
    public String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "cod_utente", nullable = false, length = 50)
    public TipoUtente codUtente;

    @Column(name = "data_creazione", nullable = false)
    public LocalDateTime dataCreazione;

    @Column(name = "data_ultimo_accesso")
    public LocalDateTime dataUltimoAccesso;

    @Column(name = "attivo", nullable = false)
    public Boolean attivo = true;

    // Relazione con i ruoli
    @OneToMany(mappedBy = "authentication", fetch = FetchType.EAGER)
    public Set<AuthenticationRoleEntity> authenticationRoles;

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

    public static Authentication findByIdUser(Long idUser) {
        return find("idUser", idUser).firstResult();
    }

    public static Authentication findByIdUserAndTipo(Long idUser, TipoUtente tipo) {
        return find("idUser = ? 1 and codUtente = ?2", idUser, tipo).firstResult();
    }

    public static List<Authentication> findAllActive() {
        return list("attivo", true);
    }

    public static List<Authentication> findByTipoUtente(TipoUtente tipo) {
        return list("codUtente", tipo);
    }

    public void updateLastAccess() {
        this.dataUltimoAccesso = LocalDateTime.now();
    }
}