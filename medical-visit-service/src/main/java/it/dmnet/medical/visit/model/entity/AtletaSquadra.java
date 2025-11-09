package it.dmnet.medical.visit.model.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(
        name = "atleta_squadra",
        uniqueConstraints = @UniqueConstraint(columnNames = {"atleta_id", "squadra_id"})
)
public class AtletaSquadra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ManyToOne: molti record AtletaSquadra per uno Atleta
    @ManyToOne
    @JoinColumn(name = "atleta_id", nullable = false)
    private AtletaEntity atleta;

    @ManyToOne
    @JoinColumn(name = "squadra_id", nullable = false)
    private SquadraEntity squadra;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    // Costruttori
    public AtletaSquadra() {}

    public AtletaSquadra(AtletaEntity atleta, SquadraEntity squadra) {
        this.atleta = atleta;
        this.squadra = squadra;
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = OffsetDateTime.now();
        }
    }

    // Getter e Setter
    public Long getId() {
        return id;
    }

    public AtletaEntity getAtleta() {
        return atleta;
    }

    public void setAtleta(AtletaEntity atleta) {
        this.atleta = atleta;
    }

    public SquadraEntity getSquadra() {
        return squadra;
    }

    public void setSquadra(SquadraEntity squadra) {
        this.squadra = squadra;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}