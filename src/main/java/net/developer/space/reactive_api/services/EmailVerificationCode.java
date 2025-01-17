package net.developer.space.reactive_api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailVerificationCode implements IEmailVerificationService{

    private final EmailService service;

    @Override
    public String generateVerificationCode() {
        Random random = new Random();
        int verificationCode = random.nextInt(999999);
        return String.format("%06d", verificationCode);
    }

    @Override
    public void sendVerificationCode(String email, String code) {
        service.sendEmail(email, "Verification Code", "Please confirm your login step with this code: " + code);
    }

    @Override
    public boolean verifyCode(String userEmail, String userEnteredCode, String sentCode) {
        return sentCode.equals(userEnteredCode);
    }
}
