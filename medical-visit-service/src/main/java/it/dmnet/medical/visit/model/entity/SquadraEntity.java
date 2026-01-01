package it.dmnet.medical.visit.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

    // Relazione OneToMany con la tabella associativa
    @OneToMany(mappedBy = "squadra", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<AtletaSquadraEntity> atletiSquadra = new ArrayList<>();

    @Override
    public String toString() {
        return "SquadraEntity{" +
                "id=" + id +
                ", idAllenatore=" + idAllenatore +
                ", idAnnoAgonistico=" + idAnnoAgonistico +
                ", nome=" + nome +
                '}';
    }

    // Metodi helper per gestire la relazione bidirezionale
    public void addAtleta(AtletaSquadraEntity atletaSquadra) {
        atletiSquadra.add(atletaSquadra);
        atletaSquadra.setSquadra(this);
    }

    public void removeAtleta(AtletaSquadraEntity atletaSquadra) {
        atletiSquadra.remove(atletaSquadra);
        atletaSquadra.setSquadra(null);
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

    public List<AtletaSquadraEntity> getAtletiSquadra() {
        return atletiSquadra;
    }

    public void setAtletiSquadra(List<AtletaSquadraEntity> atletiSquadra) {
        this.atletiSquadra = atletiSquadra;
    }


}
