package it.dmnet.medical.visit.service.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Map;

@Path("/app-config")
public class AppConfigResource {


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> config() {
        String apiBase = System.getenv().getOrDefault("API_BASE_URL", "/");
        return Map.of("apiBaseUrl", apiBase);
    }
}

