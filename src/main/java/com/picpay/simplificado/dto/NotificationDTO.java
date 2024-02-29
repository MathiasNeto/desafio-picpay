package com.picpay.simplificado.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDTO {
    private String email;
    private String message;

    public NotificationDTO(String email, String message) {
        this.message = message;
        this.email = email;
    }
}
