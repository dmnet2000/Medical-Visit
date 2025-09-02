package it.dmnet.medical.visit.service.rest;

import it.dmnet.medical.visit.model.dto.Atleta;
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

    Logger log = Logger.getLogger(UploadResource.class.getName());


    @POST
    @Path("/import-excel")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response importExcel(@RestForm("file") File file) {
        log.info("invocazione rest import-excel:"+ file.getName());
        try {
            List<Atleta> atleti = excelImportService.importAtletiFromExcle(file);

            atleti.forEach(atleta -> {
                log.info(atleta.cognome);
            });

            return Response.ok(atleti).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Errore nell'importazione: " + e.getMessage())
                    .build();
        }
    }


}