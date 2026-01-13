package it.dmnet.medical.visit.service.bo;

import io.quarkus.elytron.security.common.BcryptUtil;
import it.dmnet.medical.visit.model.entity.Authentication;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;

@ApplicationScoped
public class LoginService {

    private static final int MAX_TENTATIVI = 5;
    private static final int MINUTI_BLOCCO = 15;

    /**
     * Login con gestione tentativi falliti
     */
    @Transactional
    public LoginResult login(String username, String plainPassword) {

        // 1. Cerca utente per username
        Authentication auth = Authentication.findByUsername(username);

        if (auth == null) {
            return LoginResult.failed("Username non trovato");
        }

        // 2. Verifica se account è attivo
        if (!auth.attivo) {
            return LoginResult.failed("Account disabilitato");
        }

        // 3. Verifica se account è bloccato (opzionale - richiede campo bloccato_fino)
        // if (auth.bloccatoFino != null && auth.bloccatoFino. isAfter(LocalDateTime.now())) {
        //     return LoginResult.failed("Account temporaneamente bloccato.  Riprova più tardi");
        // }

        // 4. VERIFICA PASSWORD con BCrypt
        boolean passwordCorretta = BcryptUtil.matches(plainPassword, auth. passwordHash);

        if (!passwordCorretta) {
            // Password sbagliata - incrementa tentativi falliti
            return handleFailedLogin(auth);
        }

        // 5. LOGIN RIUSCITO
        // Reset tentativi falliti e aggiorna ultimo accesso
        auth.dataUltimoAccesso = LocalDateTime.now();
        // auth.tentativiFalliti = 0;  // se hai questo campo
        // auth.bloccatoFino = null;    // se hai questo campo
        auth.persist();

        return LoginResult.success(auth);
    }

    /**
     * Gestione tentativi falliti (opzionale - richiede campi aggiuntivi)
     */
    private LoginResult handleFailedLogin(Authentication auth) {
        // Se hai i campi tentativi_falliti e bloccato_fino:
        // auth.tentativiFalliti++;
        //
        // if (auth.tentativiFalliti >= MAX_TENTATIVI) {
        //     auth.bloccatoFino = LocalDateTime.now().plusMinutes(MINUTI_BLOCCO);
        //     auth.persist();
        //     return LoginResult.failed("Troppi tentativi falliti. Account bloccato per " + MINUTI_BLOCCO + " minuti");
        // }
        //
        // auth.persist();
        // int tentativiRimanenti = MAX_TENTATIVI - auth. tentativiFalliti;
        // return LoginResult.failed("Password errata. Tentativi rimanenti: " + tentativiRimanenti);

        // Versione semplice senza conteggio tentativi:
        return LoginResult.failed("Username o password errati");
    }

    /**
     * Classe risultato login
     */
    public static class LoginResult {
        public boolean success;
        public String message;
        public Authentication authentication;

        public static LoginResult success(Authentication auth) {
            LoginResult result = new LoginResult();
            result.success = true;
            result.message = "Login effettuato con successo";
            result.authentication = auth;
            return result;
        }

        public static LoginResult failed(String message) {
            LoginResult result = new LoginResult();
            result.success = false;
            result.message = message;
            return result;
        }
    }
}