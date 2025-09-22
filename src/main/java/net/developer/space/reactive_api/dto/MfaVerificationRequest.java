package net.developer.space.reactive_api.dto;

import lombok.Data;

@Data
public class MfaVerificationRequest {
    private String username;
    private String totp;
}
