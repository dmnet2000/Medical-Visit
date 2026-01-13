package it.dmnet.medical.visit.model.dto;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SuccessResponse {
    public String message;
    public Long userId;
    public String username;

    public SuccessResponse(String message, Long userId, String username) {
        this.message = message;
        this.userId = userId;
        this.username = username;
    }
}