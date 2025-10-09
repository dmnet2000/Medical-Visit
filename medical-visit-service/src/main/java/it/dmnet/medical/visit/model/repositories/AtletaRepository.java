package it.dmnet.medical.visit.model.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import it.dmnet.medical.visit.model.entity.AtletaEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDate;

@ApplicationScoped
public class AtletaRepository implements PanacheRepository<AtletaEntity> {
    @Transactional
    public AtletaEntity findByCodiceFiscale(String codiceFiscale) {
        return find("codiceFiscale", codiceFiscale).firstResult();
    }

    public int updateDataScadenzaVisitaMedica(String codiceFiscale, LocalDate nuovaData) {
        return update("dataScadenzaVisitaMedica = ?1 where codiceFiscale = ?2", nuovaData, codiceFiscale);
    }

}
