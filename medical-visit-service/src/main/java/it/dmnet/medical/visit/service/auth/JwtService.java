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
        var builder = Jwt.issuer(issuer)
                .upn(username)
                .groups(roles)
                .claim("tipo_utente", tipoUtente)
                .expiresIn(Duration.ofHours(expirationHours));

        if (userId != null) {
            builder.claim("user_id", userId);
        }

        return builder.sign();
    }

}
