package it.dmnet.medical.visit.service.rest;

import it.dmnet.medical.visit.model.dto.Atleta;
import it.dmnet.medical.visit.service.excel.ExcelImportService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestForm;

import java.io.InputStream;
import java.util.List;

@Path("/atleti")
public class UploadResource {

    @Inject
    ExcelImportService excelImportService;


    @POST
    @Path("/import-excel")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response importExcel(@RestForm("file") InputStream file) {
        try {
            List<Atleta> atleti = excelImportService.importAtletiFromExcle(file);
            return Response.ok(atleti).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Errore nell'importazione: " + e.getMessage())
                    .build();
        }
    }

    // Classe helper per upload
    public static class FileUploadForm {
        @FormParam("file")
        public InputStream file;
    }
}