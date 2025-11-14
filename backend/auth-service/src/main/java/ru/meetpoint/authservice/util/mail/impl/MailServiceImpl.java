package ru.meetpoint.authservice.util.mail.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.meetpoint.authservice.config.property.MailConfigProperties;
import ru.meetpoint.authservice.util.mail.MailService;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final MailConfigProperties mailConfigProperties;

    private final JavaMailSender javaMailSender;

    @Override
    public void sendVerificationCodeToEmail(String email, String verificationCode) {
        String text = mailConfigProperties.verificationCodeText().concat(" ").concat(verificationCode);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(mailConfigProperties.senderUsername());
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(mailConfigProperties.verificationCodeSubject());
        simpleMailMessage.setText(text);

        javaMailSender.send(simpleMailMessage);
    }
}
