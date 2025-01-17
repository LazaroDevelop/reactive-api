package net.developer.space.reactive_api.services;

public interface IEmailService {
    void sendEmail(String to, String subject, String text);
}
