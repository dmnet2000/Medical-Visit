package it.dmnet.medical.visit.model.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import it.dmnet.medical.visit.model.entity.SquadraEntity;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class SquadraRepository implements PanacheRepository<SquadraEntity> {

    public SquadraEntity findByIdWithAtleti(Long id) {
        return find("SELECT s FROM SquadraEntity s LEFT JOIN FETCH s.atletiSquadra WHERE s.id = ?1", id)
                .firstResult();
    }

}
