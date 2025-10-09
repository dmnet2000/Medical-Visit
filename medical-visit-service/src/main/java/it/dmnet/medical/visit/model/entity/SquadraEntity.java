package it.dmnet.medical.visit.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "squadra")
public class SquadraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false,name = "id_allenatore")
    public Long idAllenatore;

    @Column(nullable = false,name = "id_anno_agonistico")
    public Long idAnnoAgonistico;

    @Column(nullable = false, name="nome")
    public String nome;

    @Override
    public String toString() {
        return "SquadraEntity{" +
                "id=" + id +
                ", idAllenatore=" + idAllenatore +
                ", idAnnoAgonistico=" + idAnnoAgonistico +
                ", nome=" + nome +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAllenatore() {
        return idAllenatore;
    }

    public void setIdAllenatore(Long idAllenatore) {
        this.idAllenatore = idAllenatore;
    }

    public Long getIdAnnoAgonistico() {
        return idAnnoAgonistico;
    }

    public void setIdAnnoAgonistico(Long idAnnoAgonistico) {
        this.idAnnoAgonistico = idAnnoAgonistico;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
