package it.dmnet.medical.visit.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cod_role", unique = true, nullable = false, length = 50)
    private String codRole;

    @Column(name = "description", length = 255)
    private String description;

    // Getter e Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodRole() {
        return codRole;
    }

    public void setCodRole(String codRole) {
        this.codRole = codRole;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}