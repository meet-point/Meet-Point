package ru.meetpoint.authservice.util.mail;

public interface MailService {

    void sendVerificationCodeToEmail(String email, String verificationCode);
}
