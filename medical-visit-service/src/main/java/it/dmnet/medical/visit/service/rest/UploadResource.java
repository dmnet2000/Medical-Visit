package it.dmnet.medical.visit.service.rest;

import it.dmnet.medical.visit.model.dto.Atleta;
import it.dmnet.medical.visit.service.bo.AtletaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.io.InputStream;

@Path("/upload")
public class UploadResource {

    @Inject
    AtletaService atletaService;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadExcel(@FormParam("file") InputStream fileInputStream) {
        try {
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // Assumendo la prima riga come intestazione
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                String cognome = row.getCell(0).getStringCellValue();
                String nome = row.getCell(1).getStringCellValue();
                String codiceFiscale = row.getCell(2).getStringCellValue();
                LocalDate dataNascita = LocalDate.parse(row.getCell(3).getStringCellValue(), formatter);
                LocalDate dataScadenza = LocalDate.parse(row.getCell(4).getStringCellValue(), formatter);

                Atleta atleta = new Atleta(cognome, nome, codiceFiscale, dataNascita, dataScadenza);
                atletaService.addPersona(atleta);
            }
            workbook.close();
            return Response.ok("File caricato e dati salvati!").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Errore durante l'elaborazione del file: " + e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersone() {
        return Response.ok(atletaService.getAtleta()).build();
    }
}