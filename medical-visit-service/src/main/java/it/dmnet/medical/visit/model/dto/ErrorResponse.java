package it.dmnet.medical.visit.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ErrorResponse {
    public String error;

    public ErrorResponse(String error) {
        this.error = error;
    }
}