package it.dmnet.medical.visit.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MessageResponse {
    public String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}