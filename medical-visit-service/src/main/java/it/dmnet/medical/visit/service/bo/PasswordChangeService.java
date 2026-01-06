package it.dmnet.medical.visit.service.bo;

import io.quarkus.elytron.security.common.BcryptUtil;
import it.dmnet.medical.visit.model.entity.Authentication;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PasswordChangeService {

    /**
     * Cambio password con verifica password vecchia
     */
    @Transactional
    public void changePassword(Long authId, String oldPassword, String newPassword) {

        // 1. Trova l'utente
        Authentication auth = Authentication.findById(authId);
        if (auth == null) {
            throw new IllegalArgumentException("Account non trovato");
        }

        // 2. VERIFICA password attuale con BCrypt
        if (!BcryptUtil.matches(oldPassword, auth.passwordHash)) {
            throw new IllegalArgumentException("Password attuale non corretta");
        }

        // 3. Valida nuova password
        validatePasswordStrength(newPassword);

        // 4. Verifica che la nuova password sia diversa dalla vecchia
        if (BcryptUtil.matches(newPassword, auth.passwordHash)) {
            throw new IllegalArgumentException("La nuova password deve essere diversa dalla precedente");
        }

        // 5. HASH della nuova password e salvataggio
        auth.passwordHash = BcryptUtil.bcryptHash(newPassword);
        // auth.dataUltimoCambioPassword = LocalDateTime.now(); // se hai questo campo
        auth.persist();
    }

    /**
     * Reset password (es. "password dimenticata") - SENZA verifica vecchia password
     * Da usare SOLO con token di reset via email
     */
    @Transactional
    public void resetPassword(String username, String newPassword, String resetToken) {

        Authentication auth = Authentication.findByUsername(username);
        if (auth == null) {
            throw new IllegalArgumentException("Username non trovato");
        }

        // Verifica token di reset (richiede campi token_reset_password e scadenza_token_reset)
        // if (auth.tokenResetPassword == null || ! auth.tokenResetPassword.equals(resetToken)) {
        //     throw new IllegalArgumentException("Token di reset non valido");
        // }
        //
        // if (auth. scadenzaTokenReset == null || auth.scadenzaTokenReset.isBefore(LocalDateTime.now())) {
        //     throw new IllegalArgumentException("Token di reset scaduto");
        // }

        // Valida e salva nuova password
        validatePasswordStrength(newPassword);
        auth.passwordHash = BcryptUtil.bcryptHash(newPassword);

        // Invalida il token
        // auth.tokenResetPassword = null;
        // auth.scadenzaTokenReset = null;

        auth.persist();
    }

    private void validatePasswordStrength(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("La password deve essere almeno 8 caratteri");
        }

        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLower = password.chars().anyMatch(Character:: isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);

        if (!hasUpper || !hasLower || !hasDigit) {
            throw new IllegalArgumentException(
                    "La password deve contenere:  maiuscola, minuscola, numero"
            );
        }
    }
}