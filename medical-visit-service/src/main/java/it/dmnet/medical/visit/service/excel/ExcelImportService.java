package it.dmnet.medical.visit.service.excel;

import it.dmnet.medical.visit.model.dto.Atleta;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ExcelImportService {
    public List<Atleta> importAtletiFromExcle(InputStream is) throws IOException {
        List<Atleta> atleti = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Salta la prima riga se è intestazione
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Atleta atleta = new Atleta();
            atleta.setCognome(getStringCellValue(row.getCell(0)));
            atleta.setNome(getStringCellValue(row.getCell(1)));
            atleta.setCodiceFiscale(getStringCellValue(row.getCell(2)));
            atleta.setDataNascita(getLocalDateCellValue(row.getCell(3), formatter));
            atleta.setDataScadenzaVisitaMedica(getLocalDateCellValue(row.getCell(4), formatter));

            atleti.add(atleta);
        }
        workbook.close();
        return atleti;
    }

    private String getStringCellValue(Cell cell) {
        return cell != null ? cell.getStringCellValue().trim() : "";
    }

    private LocalDate getLocalDateCellValue(Cell cell, DateTimeFormatter formatter) {
        if (cell == null) return null;
        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getLocalDateTimeCellValue().toLocalDate();
        }
        if (cell.getCellType() == CellType.STRING) {
            return LocalDate.parse(cell.getStringCellValue().trim(), formatter);
        }
        return null;
    }

}
