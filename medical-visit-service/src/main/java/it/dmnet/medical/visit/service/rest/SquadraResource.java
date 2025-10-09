package it.dmnet.medical.visit.service.rest;

import it.dmnet.medical.visit.model.dto.SquadraDTO;
import it.dmnet.medical.visit.model.entity.SquadraEntity;
import it.dmnet.medical.visit.model.repositories.AllenatoreRepository;
import it.dmnet.medical.visit.model.repositories.SquadraRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.logging.Log;
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
    AllenatoreRepository allenatoreRepositories;

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
    public Response getListaSquadre() {
        List<SquadraEntity> listaSquadre = repository.listAll();
        List<SquadraDTO> squadraDto = new ArrayList<>();
        for( SquadraEntity entity : listaSquadre){
            SquadraDTO squadra = new SquadraDTO();
            //allenatoreRepositories.
            //squadra.setNomeSquadra(entity.);
        }
        return Response.ok(squadraDto).build();
    }
}






