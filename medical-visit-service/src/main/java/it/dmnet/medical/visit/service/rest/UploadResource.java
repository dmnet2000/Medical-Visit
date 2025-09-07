package it.dmnet.medical.visit.service.rest;

import it.dmnet.medical.visit.model.dto.Atleta;
import it.dmnet.medical.visit.model.dto.AtletaForm;
import it.dmnet.medical.visit.model.entity.AtletaEntity;
import it.dmnet.medical.visit.service.bo.AtletaService;
import it.dmnet.medical.visit.service.excel.ExcelImportService;
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
import java.util.List;

@Path("/atleti")
public class UploadResource {

    @Inject
    ExcelImportService excelImportService;

    @Inject
    AtletaService atletaService;

    Logger log = Logger.getLogger(UploadResource.class.getName());


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
        return atletaService.search(atletaForm.getCognome(), atletaForm.getNome(), atletaForm.getCodiceFiscale());
    }


}