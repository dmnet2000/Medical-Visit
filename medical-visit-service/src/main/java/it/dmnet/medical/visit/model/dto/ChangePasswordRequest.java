package it.dmnet.medical.visit.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {
    public Long authId;
    public String oldPassword;
    public String newPassword;
}