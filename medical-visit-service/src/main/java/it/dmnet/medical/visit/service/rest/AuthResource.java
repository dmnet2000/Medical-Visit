package it.dmnet.medical.visit.service.rest;

import it.dmnet.medical.visit.model.dto.*;
import it.dmnet.medical.visit.model.dto.auth.AuthResponse;
import it.dmnet.medical.visit.model.dto.auth.RegisterRequest;
import it.dmnet.medical.visit.service.auth.AuthService;
import it.dmnet.medical.visit.service.bo.LoginService;
import it.dmnet.medical.visit.service.bo.PasswordChangeService;
import it.dmnet.medical.visit.service.bo.RegistrationService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    Logger log = Logger.getLogger(AuthResource.class.getName());

    @Inject
    RegistrationService registrationService;

    @Inject
    AuthService authService;

    @Inject
    PasswordChangeService passwordChangeService;

    /**
     * POST /api/auth/register
     * Registrazione nuovo utente
     */
    @POST
    @Path("/register")
    @PermitAll
    public Response register(RegisterRequest request) {
        try {
            var auth = registrationService.registerNewUser(
                    request.idUser,
                    request.username,
                    request.password
            );

            return Response.status(Response.Status.CREATED)
                    .entity(new SuccessResponse(
                            "Registrazione completata con successo",
                            auth.id,
                            auth.username
                    ))
                    .build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    /**
     * POST /api/auth/login
     * Login utente
     */
    @POST
    @Path("/login")
    @PermitAll
    public Response login(LoginRequest request) {
        log.info("log:" + request.getUsername());
        AuthResponse response = authService.login(request);
        return Response.ok(response).build();  // Con JWT!
    }

    /**
     * PUT /api/auth/change-password
     * Cambio password
     */
    @PUT
    @Path("/change-password")
    @PermitAll
    public Response changePassword(ChangePasswordRequest request) {
        try {
            passwordChangeService.changePassword(
                    request.authId,
                    request.oldPassword,
                    request.newPassword
            );

            return Response.ok(new MessageResponse("Password cambiata con successo")).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

}