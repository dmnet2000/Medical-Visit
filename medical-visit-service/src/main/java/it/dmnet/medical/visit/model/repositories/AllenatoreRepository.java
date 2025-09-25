package it.dmnet.medical.visit.model.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import it.dmnet.medical.visit.model.entity.AllenatoreEntity;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class AllenatoreRepository implements PanacheRepository<AllenatoreEntity> {
    // Puoi aggiungere metodi personalizzati qui
}