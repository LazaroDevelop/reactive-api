package net.developer.space.reactive_api.services;

public interface IEmailVerificationService {
    String generateVerificationCode();
    void sendVerificationCode(String email, String code);
    boolean verifyCode(String userEmail, String userEnteredCode, String sentCode);
}
