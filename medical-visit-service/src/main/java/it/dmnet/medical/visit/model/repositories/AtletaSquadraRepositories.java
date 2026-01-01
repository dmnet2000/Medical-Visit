package it.dmnet.medical.visit.model.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import it.dmnet.medical.visit.model.entity.AtletaSquadraEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AtletaSquadraRepositories implements PanacheRepository<AtletaSquadraEntity> {
    // Puoi aggiungere metodi custom se necessario
    public boolean existsByAtletaAndSquadra(Long atletaId, Long squadraId) {
        return count("atleta. id = ? 1 and squadra.id = ?2", atletaId, squadraId) > 0;
    }
}