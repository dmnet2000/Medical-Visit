package it.dmnet.medical.visit.service.auth;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.util.Set;

@ApplicationScoped
public class JwtService {
    @ConfigProperty(name = "mp.jwt.verify.issuer", defaultValue = "medical-visit")
    String issuer;

    @ConfigProperty(name = "jwt.expiration.hours", defaultValue = "24")
    Long expirationHours;

    /**
     * Genera token JWT con username, ruoli e tipo utente
     */
    public String generateToken(String username, Set<String> roles, String tipoUtente, Long userId) {
        return Jwt.issuer(issuer)
                .upn(username)  // Username come "User Principal Name"
                .groups(roles)  // Ruoli dell'utente
                .claim("tipo_utente", tipoUtente)  // Custom claim
                .claim("user_id", userId)  // ID dell'utente (atleta/allenatore)
                .expiresIn(Duration.ofHours(expirationHours))
                .sign();
    }

}
