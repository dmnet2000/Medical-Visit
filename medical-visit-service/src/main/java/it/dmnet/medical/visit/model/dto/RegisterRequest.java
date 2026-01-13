package it.dmnet.medical.visit.model.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterRequest {
    public Long idAllenatore;
    public String username;
    public String password;
}