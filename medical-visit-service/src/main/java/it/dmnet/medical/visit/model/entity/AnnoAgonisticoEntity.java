package it.dmnet.medical.visit.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "anno_agonistico")
public class AnnoAgonisticoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String anno;
    // Getter e Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAnno() { return anno; }
    public void setAnno(String anno) { this.anno = anno; }

    @Override
    public String toString() {
        return "AnnoAgonisticoEntity{" +
                "id=" + id +
                ", anno='" + anno + '\'' +
                '}';
    }
}
