package it.dmnet.medical.visit.model.dto.auth;

import it.dmnet.medical.visit.model.enumeration.TipoUtente;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterRequest {
    public Long idUser;  // Può essere id atleta o id allenatore
    public String username;
    public String password;
    public TipoUtente tipoUtente;  // ATLETA, ALLENATORE, ADMIN
}