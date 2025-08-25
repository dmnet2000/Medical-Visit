package it.dmnet.medical.visit.service.bo;

import it.dmnet.medical.visit.model.dto.Atleta;

import java.util.ArrayList;
import java.util.List;

public class AtletaService {
    private final List<Atleta> atleta = new ArrayList<>();

    public void addPersona(Atleta persona) {
        atleta.add(persona);
    }

    public List<Atleta> getAtleta() {
        return atleta;
    }

    public void clearAtleta() {
        atleta.clear();
    }
}