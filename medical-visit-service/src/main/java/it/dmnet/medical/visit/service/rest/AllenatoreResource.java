package it.dmnet.medical.visit.service.rest;

import it.dmnet.medical.visit.model.entity.AllenatoreEntity;
import it.dmnet.medical.visit.model.repositories.AllenatoreRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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

    @PUT
    @Path("/update/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, AllenatoreEntity allenatore) {
        AllenatoreEntity entity = repository.findById(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        entity.nome = allenatore.nome;
        entity.cognome = allenatore.cognome;
        entity.indirizzo = allenatore.indirizzo;
        entity.indirizzoMail = allenatore.indirizzoMail;
        entity.numeroTelefono = allenatore.numeroTelefono;
        entity.codiceFiscale = allenatore.codiceFiscale;
        return Response.ok(entity).build();
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
    @Path("/search")
    public List<AllenatoreEntity> search(
            @QueryParam("nome") String nome,
            @QueryParam("cognome") String cognome
    ) {
        if ((nome == null || nome.isBlank()) && (cognome == null || cognome.isBlank())) {
            // Nessun filtro, restituisci tutti
            return repository.listAll();
        }
        // Costruisci la query dinamica
        String query = "1=1";
        if (nome != null && !nome.isBlank()) {
            query += " and lower(nome) like ?1";
        }
        if (cognome != null && !cognome.isBlank()) {
            query += " and lower(cognome) like ?2";
        }
        // Parametri
        Object[] params = new Object[]{
                nome != null && !nome.isBlank() ? "%" + nome.toLowerCase() + "%" : null,
                cognome != null && !cognome.isBlank() ? "%" + cognome.toLowerCase() + "%" : null
        };
        // Rimuovi parametri nulli
        List<Object> filteredParams = new java.util.ArrayList<>();
        if (params[0] != null) filteredParams.add(params[0]);
        if (params[1] != null) filteredParams.add(params[1]);
        return repository.list(query, filteredParams.toArray());
    }
}