package it.dmnet.medical.visit.model.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import it.dmnet.medical.visit.model.entity.Authentication;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthenticationRepositories implements PanacheRepository<Authentication> {

    public Authentication findByIdAllenatore(Long idAllenatore) {
        return find("id_user", idAllenatore).firstResult();
    }

    public Authentication findByUsername(String username) {
        return find("username", username).firstResult();
    }
}
