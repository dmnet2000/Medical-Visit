package it.dmnet.medical.visit.model.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import it.dmnet.medical.visit.model.entity.SquadraEntity;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class SquadraRepository implements PanacheRepository<SquadraEntity> {
}
