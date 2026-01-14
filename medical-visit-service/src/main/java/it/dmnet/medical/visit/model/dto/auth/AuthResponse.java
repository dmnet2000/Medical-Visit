package it.dmnet.medical.visit.model.dto.auth;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    public String token;
    public String username;
    public String tipoUtente;
    public Set<String> roles;
    public Long userId;
}