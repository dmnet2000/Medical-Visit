package it.dmnet.medical.visit.model.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import it.dmnet.medical.visit.model.entity.AuthenticationRoleEntity;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class AuthenticationRoleRepository implements PanacheRepository<AuthenticationRoleEntity> {
    public List<AuthenticationRoleEntity> findByAuthenticationId(Long authenticationId) {
        return list("idAuthentication", authenticationId);
    }

    public List<AuthenticationRoleEntity> findActiveByAuthenticationId(Long authenticationId) {
        return list("idAuthentication = ? 1 and enable = true", authenticationId);
    }

}
