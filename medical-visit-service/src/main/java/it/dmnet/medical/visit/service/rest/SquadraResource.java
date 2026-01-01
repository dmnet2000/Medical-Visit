package it.dmnet.medical.visit.service.rest;

import it.dmnet.medical.visit.model.dto.SquadraDTO;
import it.dmnet.medical.visit.model.entity.*;
import it.dmnet.medical.visit.model.repositories.*;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.List;

@Path("/squadra")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SquadraResource {

    @Inject
    SquadraRepository repository;


    @Inject
    AtletaRepository atletaRepository;

    @Inject
    AllenatoreRepository allenatoreRepositories;

    @Inject
    AnnoAgonisticoRepository annoAgonisticoRepository;

    @Inject
    AtletaSquadraRepositories atletaSquadraRepositories;

    Logger log = Logger.getLogger(SquadraResource.class.getName());

    @POST
    @Path("/insert")
    @Transactional
    public Response create(SquadraEntity squadra) {
        log.info("Squadra: " + squadra);
        repository.persist(squadra);
        return Response.ok(squadra).build();
    }

    @GET
    @Path("listaSquadre")
    @Transactional
    public List<SquadraDTO> getListaSquadre() {
        List<SquadraEntity> listaSquadre = repository.listAll();
        List<SquadraDTO> listaSquadreView = new ArrayList<>();
        for (SquadraEntity entity : listaSquadre) {
            SquadraDTO squadra = new SquadraDTO();
            squadra.setId(entity.getId());
            AllenatoreEntity allenatore = allenatoreRepositories.findById(entity.idAllenatore);
            squadra.setNomeAllenatore(allenatore.nome + " " + allenatore.cognome);
            squadra.setNomeSquadra(entity.getNome());
            AnnoAgonisticoEntity anno = annoAgonisticoRepository.findById(entity.getIdAnnoAgonistico());
            squadra.setAnnoAgonistico(anno.getAnno());
            listaSquadreView.add(squadra);

        }
        return listaSquadreView;
    }

    @DELETE
    @Path("/delete/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = repository.deleteById(id);
        if (deleted) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("getDetail/{id}")
    @Transactional
    public SquadraEntity getSquadraDetail(@PathParam("id") Long id) {
        return repository.findByIdWithAtleti(id);
    }

    @POST
    @Path("/{id}/associa-atleti")
    @Transactional
    public Response insertAtletiSquadra(@PathParam("id") Long squadraId, List<Long> atletaIds) {
        SquadraEntity squadra = repository.findById(squadraId);
        if (squadra == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Squadra non trovata")
                    .build();
        }
        for (Long atletaId : atletaIds) {
            // Recupera l'atleta dal database
            AtletaEntity atleta = atletaRepository.findById(atletaId);

            if (atleta == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Atleta con ID " + atletaId + " non trovato")
                        .build();
            }

            // Verifica se l'associazione esiste già (opzionale)
            boolean esisteGia = squadra.getAtletiSquadra().stream()
                    .anyMatch(as -> as.getAtleta().getId().equals(atletaId));

            if (!esisteGia) {
                // Crea la nuova associazione
                AtletaSquadraEntity atletaSquadra = new AtletaSquadraEntity(atleta, squadra);

                // Aggiungi usando il metodo helper (se l'hai implementato)
                squadra.addAtleta(atletaSquadra);

                // OPPURE aggiungi direttamente alla lista
                // squadra.getAtletiSquadra().add(atletaSquadra);

                // Persisti l'associazione
                atletaSquadraRepositories.persist(atletaSquadra);
            }
        }


        return Response.ok().build();

    }

}






