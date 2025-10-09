package it.dmnet.medical.visit.model.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import it.dmnet.medical.visit.model.entity.AnnoAgonisticoEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AnnoAgonisticoRepository implements PanacheRepository<AnnoAgonisticoEntity> {

}
