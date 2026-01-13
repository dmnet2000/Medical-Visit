package it.dmnet.medical.visit.model.dto;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    public String username;
    public String password;
}
