package it.dmnet.medical.visit.service.rest;

import it.dmnet.medical.visit.model.entity.AllenatoreEntity;
import it.dmnet.medical.visit.model.repositories.AllenatoreRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;


@Path("/allenatori")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AllenatoreResource {

    @Inject
    AllenatoreRepository repository;

    @GET
    @Path("/list")
    public List<AllenatoreEntity> list() {
        return repository.listAll();
    }

    @POST
    @Path("/insert")
    @Transactional
    public AllenatoreEntity create(AllenatoreEntity allenatore) {
        repository.persist(allenatore);
        return allenatore;
    }
}