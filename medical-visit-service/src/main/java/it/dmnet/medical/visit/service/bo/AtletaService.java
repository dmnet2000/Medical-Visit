package it.dmnet.medical.visit.service.bo;


import it.dmnet.medical.visit.model.dto.Atleta;
import it.dmnet.medical.visit.model.repositories.AtletaRepositories;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class AtletaService {
    @Inject
    AtletaRepositories atletaRepository;
    Logger log = Logger.getLogger(AtletaService.class.getName());

    @Transactional
    public void updateAtleti(List<Atleta> listaAtleti) {
        log.info("updateAtleti:");
        for (Atleta atleta : listaAtleti) {
             log.info("atleta:" + atleta);
            it.dmnet.medical.visit.model.entity.Atleta existing = atletaRepository.findByCodiceFiscale(atleta.getCodiceFiscale());
            log.info("atleta ricercata");
            if (existing != null) {
                log.info("atleta esiste" );
                existing.setCognome(atleta.getCognome());
                existing.setNome(atleta.getNome());
                existing.setDataDiNascita(atleta.getDataNascita());
                existing.setDataScadenzaVisitaMedica(atleta.getDataScadenzaVisitaMedica());
                atletaRepository.persist(existing);
            } else {
                log.info("atleta non esiste");
                it.dmnet.medical.visit.model.entity.Atleta atletaEntity = new it.dmnet.medical.visit.model.entity.Atleta();
                atletaEntity.setCodiceFiscale(atleta.getCodiceFiscale());
                atletaEntity.setCognome(atleta.getCognome());
                atletaEntity.setNome(atleta.getNome());
                atletaEntity.setDataDiNascita(atleta.getDataNascita());
                atletaEntity.setDataScadenzaVisitaMedica(atleta.getDataScadenzaVisitaMedica());
                atletaRepository.persist(atletaEntity);
            }
        }
    }
}