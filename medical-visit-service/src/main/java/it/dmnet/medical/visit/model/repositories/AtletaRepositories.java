package it.dmnet.medical.visit.model.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import it.dmnet.medical.visit.model.entity.Atleta;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AtletaRepositories implements PanacheRepository<Atleta> {

    public Atleta findByCodiceFiscale(String codiceFiscale) {
        return find("codiceFiscale", codiceFiscale).firstResult();
    }


}
