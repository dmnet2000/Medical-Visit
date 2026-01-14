package it.dmnet.medical.visit.model.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import it.dmnet.medical.visit.model.entity.RoleEntity;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class RoleRepositories implements PanacheRepository<RoleEntity> {
    public Optional<RoleEntity> findByCodRole(String codRole) {
        return find("codRole", codRole).firstResultOptional();
    }
}
