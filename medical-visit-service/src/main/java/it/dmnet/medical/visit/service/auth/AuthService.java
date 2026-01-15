package it.dmnet.medical.visit.service.auth;

import io.quarkus.elytron.security.common.BcryptUtil;

import it.dmnet.medical.visit.model.dto.LoginRequest;
import it.dmnet.medical.visit.model.dto.auth.AuthResponse;
import it.dmnet.medical.visit.model.dto.auth.RegisterRequest;
import it.dmnet.medical.visit.model.entity.Authentication;
import it.dmnet.medical.visit.model.entity.AuthenticationRoleEntity;
import it.dmnet.medical.visit.model.entity.RoleEntity;
import it.dmnet.medical.visit.model.enumeration.TipoUtente;
import it.dmnet.medical.visit.model.repositories.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotAuthorizedException;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class AuthService {

    @Inject
    RoleRepositories roleRepository;

    @Inject
    AuthenticationRoleRepository authRoleRepository;

    @Inject
    JwtService jwtService;

    @Inject
    AllenatoreRepository allenatoreRepository;

    @Inject
    AtletaRepository atletaRepository;

    @Inject
    AuthenticationRepositories authRepo;

    /**
     * Registrazione nuovo utente generico
     */
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // 1. Validazione username univoco
        if (authRepo.findByUsername(request.getUsername()) != null) {
            throw new BadRequestException("Username già esistente");
        }

        // 2. Validazione password
        validatePasswordStrength(request.getPassword());

        // 3. Validazione che l'utente esista (atleta o allenatore)
        if (request.getIdUser() != null) {
            validateUserExists(request.getIdUser(), request.getTipoUtente());

            // Verifica se già registrato
            Authentication existing = Authentication.findByIdUserAndTipo(
                    request.getIdUser(),
                    request.getTipoUtente()
            );
            if (existing != null) {
                throw new BadRequestException("Utente già registrato");
            }
        }

        // 4. Creazione authentication
        Authentication auth = new Authentication();
        auth.idUser = request.getIdUser();
        auth.username = request.getUsername();
        auth.passwordHash = BcryptUtil.bcryptHash(request.getPassword());
        auth.codUtente = request.getTipoUtente();
        auth.attivo = true;
        auth.dataCreazione = LocalDateTime.now();

        auth.persist();

        // 5. Assegnazione ruolo di default
        String defaultRole = getDefaultRoleForTipoUtente(request.getTipoUtente());
        assignRoleToUser(auth.id, defaultRole);

        // 6. Generazione JWT
        Set<String> roles = Set.of(defaultRole, "USER");
        String token = jwtService.generateToken(
                auth.username,
                roles,
                auth.codUtente.name(),
                auth.idUser
        );

        return new AuthResponse(
                token,
                auth.username,
                auth.codUtente.name(),
                roles,
                auth.idUser
        );
    }

    /**
     * Login con JWT
     */
    public AuthResponse login(LoginRequest request) {
        // 1. Trova utente
        Authentication auth = Authentication.findByUsername(request.getUsername());
        if (auth == null) {
            throw new NotAuthorizedException("Credenziali non valide");
        }

        // 2. Verifica account attivo
        if (!auth.attivo) {
            throw new NotAuthorizedException("Account disabilitato");
        }

        // 3. Verifica password
        if (!BcryptUtil.matches(request.getPassword(), auth.passwordHash)) {
            throw new NotAuthorizedException("Credenziali non valide");
        }

        // 4. Aggiorna ultimo accesso
        auth.dataUltimoAccesso = LocalDateTime.now();
        auth.persist();

        // 5. Estrai ruoli attivi
        Set<String> roles = authRoleRepository.findActiveByAuthenticationId(auth.id)
                .stream()
                .map(ar -> ar.getRole().getCodRole())
                .collect(Collectors.toSet());

        // Aggiungi sempre ruolo USER
        roles.add("USER");

        // 6. Genera JWT
        String token = jwtService.generateToken(
                auth.username,
                roles,
                auth.codUtente.name(),
                auth.idUser
        );

        return new AuthResponse(
                token,
                auth.username,
                auth.codUtente.name(),
                roles,
                auth.idUser
        );
    }

    /**
     * Assegna ruolo a utente
     */
    @Transactional
    protected void assignRoleToUser(Long authId, String codRole) {
        RoleEntity role = roleRepository.findByCodRole(codRole)
                .orElseThrow(() -> new BadRequestException("Ruolo non trovato:  " + codRole));

        AuthenticationRoleEntity authRole = new AuthenticationRoleEntity();
        authRole.setIdAuthentication(authId);
        authRole.setIdRole(role.getId());
        authRole.setEnable(true);

        authRoleRepository.persist(authRole);
    }

    /**
     * Determina ruolo di default in base al tipo utente
     */
    private String getDefaultRoleForTipoUtente(TipoUtente tipo) {
        return switch (tipo) {
            case ATLETA -> "ATLETA";
            case ALLENATORE -> "ALLENATORE";
            case ADMIN -> "ADMIN";
            default -> "USER";
        };
    }

    /**
     * Valida che l'utente esista nel database
     */
    private void validateUserExists(Long idUser, TipoUtente tipo) {
        switch (tipo) {
            case ATLETA -> {
                if (atletaRepository.findById(idUser) == null) {
                    throw new BadRequestException("Atleta non trovato con ID: " + idUser);
                }
            }
            case ALLENATORE -> {
                if (allenatoreRepository.findById(idUser) == null) {
                    throw new BadRequestException("Allenatore non trovato con ID: " + idUser);
                }
            }
            case ADMIN -> {
                // Admin può non avere idUser
            }
        }
    }

    /**
     * Validazione robustezza password
     */
    private void validatePasswordStrength(String password) {
        if (password == null || password.length() < 8) {
            throw new BadRequestException("La password deve essere almeno 8 caratteri");
        }

        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);

        if (!hasUpper || !hasLower || !hasDigit) {
            throw new BadRequestException(
                    "La password deve contenere almeno:  1 maiuscola, 1 minuscola, 1 numero"
            );
        }
    }
}
