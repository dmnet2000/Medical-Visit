package it.dmnet.medical.visit.service.bo;


import it.dmnet.medical.visit.model.dto.Atleta;
import it.dmnet.medical.visit.model.entity.AtletaEntity;
import it.dmnet.medical.visit.model.repositories.AtletaRepositories;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.ArrayList;
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
            AtletaEntity existing = atletaRepository.findByCodiceFiscale(atleta.getCodiceFiscale());
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
                AtletaEntity atletaEntity = new AtletaEntity();
                atletaEntity.setCodiceFiscale(atleta.getCodiceFiscale());
                atletaEntity.setCognome(atleta.getCognome());
                atletaEntity.setNome(atleta.getNome());
                atletaEntity.setDataDiNascita(atleta.getDataNascita());
                atletaEntity.setDataScadenzaVisitaMedica(atleta.getDataScadenzaVisitaMedica());
                atletaRepository.persist(atletaEntity);
            }
        }
    }

    public List<AtletaEntity> search(String cognome, String nome, String codiceFiscale) {

        StringBuilder query = new StringBuilder();
        List<Object> params = new ArrayList<>();
        boolean first = true;

        if (cognome != null && !cognome.isBlank()) {
            query.append(first ? "" : " and ").append("cognome = ?").append(params.size() + 1);
            params.add(cognome);
            first = false;
        }
        if (nome != null && !nome.isBlank()) {
            query.append(first ? "" : " and ").append("nome = ?").append(params.size() + 1);
            params.add(nome);
            first = false;
        }
        if (codiceFiscale != null && !codiceFiscale.isBlank()) {
            query.append(first ? "" : " and ").append("codiceFiscale = ?").append(params.size() + 1);
            params.add(codiceFiscale);
        }

        if (query.length() == 0) {
            return atletaRepository.listAll();
        }

        return atletaRepository.find(query.toString(), params.toArray()).list();
    }

}