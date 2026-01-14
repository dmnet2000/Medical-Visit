package it.dmnet.medical.visit.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "authentication_role",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_authentication", "id_role"}))
public class AuthenticationRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType. IDENTITY)
    private Long id;

    @Column(name = "id_authentication", nullable = false)
    private Long idAuthentication;

    @Column(name = "id_role", nullable = false)
    private Long idRole;

    @Column(name = "enable", nullable = false)
    private Boolean enable = true;

    @ManyToOne(fetch = FetchType. EAGER)
    @JoinColumn(name = "id_authentication", insertable = false, updatable = false)
    private Authentication authentication;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_role", insertable = false, updatable = false)
    private RoleEntity role;

    // Getter e Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAuthentication() {
        return idAuthentication;
    }

    public void setIdAuthentication(Long idAuthentication) {
        this.idAuthentication = idAuthentication;
    }

    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }
}