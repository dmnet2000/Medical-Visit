package it.dmnet.medical.visit.service.rest;

import it.dmnet.medical.visit.model.dto.*;
import it.dmnet.medical.visit.model.dto.auth.RegisterRequest;
import it.dmnet.medical.visit.service.bo.LoginService;
import it.dmnet.medical.visit.service.bo.PasswordChangeService;
import it.dmnet.medical.visit.service.bo.RegistrationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    RegistrationService registrationService;

    @Inject
    LoginService loginService;

    @Inject
    PasswordChangeService passwordChangeService;

    /**
     * POST /api/auth/register
     * Registrazione nuovo utente
     */
    @POST
    @Path("/register")
    public Response register(RegisterRequest request) {
        try {
            var auth = registrationService.registerNewUser(
                    request.idAllenatore,
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
    public Response login(LoginRequest request) {
        LoginService.LoginResult result = loginService.login(
                request.username,
                request.password
        );

        if (result.success) {
            return Response.ok(new SuccessResponse(
                    result.message,
                    result.authentication.id,
                    result.authentication.username
            )).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ErrorResponse(result.message))
                    .build();
        }
    }

    /**
     * PUT /api/auth/change-password
     * Cambio password
     */
    @PUT
    @Path("/change-password")
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