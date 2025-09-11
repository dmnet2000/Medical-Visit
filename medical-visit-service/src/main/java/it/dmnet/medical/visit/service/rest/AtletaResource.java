package it.dmnet.medical.visit.service.rest;

import it.dmnet.medical.visit.model.dto.Atleta;
import it.dmnet.medical.visit.model.dto.AtletaForm;
import it.dmnet.medical.visit.model.dto.UpdateVisitaForm;
import it.dmnet.medical.visit.model.entity.AtletaEntity;
import it.dmnet.medical.visit.service.bo.AtletaService;
import it.dmnet.medical.visit.service.excel.ExcelImportService;
import it.dmnet.medical.visit.utils.Utils;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestForm;

import java.io.File;
import java.util.Date;
import java.util.List;

@Path("/atleti")
public class AtletaResource {

    @Inject
    ExcelImportService excelImportService;

    @Inject
    AtletaService atletaService;

    Logger log = Logger.getLogger(AtletaResource.class.getName());


    @POST
    @Path("/import-excel")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response importExcel(@RestForm("file") File file) {
        log.info("invocazione rest import-excel:" + file.getName());
        try {
            List<Atleta> atleti = excelImportService.importAtletiFromExcle(file);
            //aggiornamento a db
            try {
                atletaService.updateAtleti(atleti);
            } catch (Exception ex) {
                log.error(ex);
            }
            return Response.ok(atleti).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Errore nell'importazione: " + e.getMessage())
                    .build();
        }
    }

    @POST
    @Path("/search")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<AtletaEntity> search(AtletaForm atletaForm) {
        log.info("invocazione rest search:" + atletaForm);
        return atletaService.search(atletaForm.getCognome().toUpperCase(), atletaForm.getNome().toUpperCase(), atletaForm.getCodiceFiscale().toUpperCase());
    }

    @POST
    @Path("/update-visita")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateVisitaMedica(UpdateVisitaForm updateForm) {
        if (updateForm.getCodFiscale() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (updateForm.getNewDate() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            atletaService.updateVisit(updateForm.getCodFiscale(), Utils.convertStringToLocalDate(updateForm.getNewDate()));
            return Response.ok("OK").build();
        } catch (Exception ex) {
            log.error("Errore ", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}