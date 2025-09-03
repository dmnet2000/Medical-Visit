package it.dmnet.medical.visit.service.bo;


import it.dmnet.medical.visit.model.dto.Atleta;
import it.dmnet.medical.visit.model.repositories.AtletaRepositories;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class AtletaService {
    @Inject
    AtletaRepositories atletaRepository;

    public void updateAtleti(List<Atleta> listaAtleti) {
        for (Atleta atleta : listaAtleti) {
            it.dmnet.medical.visit.model.entity.Atleta existing = atletaRepository.findByCodiceFiscale(atleta.getCodiceFiscale());
            if (existing != null) {
                existing.setCognome(atleta.getCognome());
                existing.setNome(atleta.getNome());
                existing.setDataDiNascita(atleta.getDataNascita());
                existing.setDataScadenzaVisitaMedica(atleta.getDataScadenzaVisitaMedica());
                atletaRepository.persist(existing);
            } else {
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