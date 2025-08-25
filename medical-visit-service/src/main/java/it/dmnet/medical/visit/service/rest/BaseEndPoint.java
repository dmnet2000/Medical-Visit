package it.dmnet.medical.visit.service.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/medical")
public class BaseEndPoint {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("hello")
    public String hello(){
        return "Sagugio be is Up";

    }

}
