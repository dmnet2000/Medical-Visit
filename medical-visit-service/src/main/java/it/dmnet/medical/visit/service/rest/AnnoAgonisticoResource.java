package it.dmnet.medical.visit.service.rest;

import it.dmnet.medical.visit.model.entity.AllenatoreEntity;
import it.dmnet.medical.visit.model.entity.AnnoAgonisticoEntity;
import it.dmnet.medical.visit.model.repositories.AnnoAgonisticoRepositories;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;

@Path("/anno")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AnnoAgonisticoResource {
    Logger log = Logger.getLogger(AnnoAgonisticoResource.class.getName());
    @Inject
    AnnoAgonisticoRepositories repository;

    @POST
    @Path("/insert")
    @Transactional
    public Response create(AnnoAgonisticoEntity annoAgonistico) {

        log.info(annoAgonistico);
        boolean exists = repository.find("anno", annoAgonistico.getAnno()).firstResultOptional().isPresent();
        if (exists) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Anno agonistico già presente")
                    .build();
        }
        repository.persist(annoAgonistico);
        return Response.ok(annoAgonistico).build();
    }

    @GET
    @Path("/list")
    public List<AnnoAgonisticoEntity> list() {
        return repository.listAll();
    }
}
