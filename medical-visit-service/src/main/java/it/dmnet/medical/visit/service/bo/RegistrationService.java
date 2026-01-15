package it.dmnet.medical.visit.service.bo;


import io.quarkus.elytron.security.common.BcryptUtil;
import it.dmnet.medical.visit.model.entity.AllenatoreEntity;
import it.dmnet.medical.visit.model.entity.Authentication;
import it.dmnet.medical.visit.model.repositories.AllenatoreRepository;
import it.dmnet.medical.visit.model.repositories.AuthenticationRepositories;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;

@ApplicationScoped
public class RegistrationService {

    @Inject
    AllenatoreRepository allenatoreRepositories;

    @Inject
    AuthenticationRepositories authRepositories;

    /**
     * STEP 1: Registrazione nuovo utente con validazione
     */
    @Transactional
    public Authentication registerNewUser(Long idAllenatore, String username, String plainPassword) {

        // 1. VALIDAZIONE - Verifica che l'allenatore esista
        AllenatoreEntity allenatore = allenatoreRepositories.findById(idAllenatore);
        if (allenatore == null) {
            throw new IllegalArgumentException("Allenatore non trovato con ID: " + idAllenatore);
        }

        // 2. VALIDAZIONE - Username già esistente?
        if (Authentication.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username già in uso: " + username);
        }

        // 3. VALIDAZIONE - Allenatore ha già un account?
        if (authRepositories.findByIdAllenatore(idAllenatore) != null) {
            throw new IllegalArgumentException("Allenatore ha già un account di autenticazione");
        }

        // 4. VALIDAZIONE - Password sufficientemente forte?
        validatePasswordStrength(plainPassword);

        // 5. CREAZIONE - Hash della password
        String hashedPassword = BcryptUtil.bcryptHash(plainPassword);

        // 6. CREAZIONE - Nuovo record authentication
        Authentication auth = new Authentication();
        auth.idUser = idAllenatore;
        auth.username = username;
        auth.passwordHash = hashedPassword; // ← Salviamo l'hash, NON la password
        auth.attivo = true;
        auth. dataCreazione = LocalDateTime.now();

        // 7. SALVATAGGIO nel database
        auth.persist();

        return auth;
    }

    /**
     * Validazione robustezza password
     */
    private void validatePasswordStrength(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("La password deve essere almeno 8 caratteri");
        }

        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);

        if (!hasUpper || ! hasLower || !hasDigit) {
            throw new IllegalArgumentException(
                    "La password deve contenere almeno:  1 maiuscola, 1 minuscola, 1 numero"
            );
        }
    }
}
