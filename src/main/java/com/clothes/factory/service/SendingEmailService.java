package com.clothes.factory.service;

import com.clothes.factory.mail.Mail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(
        name = "app.mail.sending.enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class SendingEmailService implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void send(final Mail mail) {
        log.info("Starting email preparation...");

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mail.getMailTo());
            message.setSubject(mail.getSubject());
            message.setText(mail.getMessage());
            javaMailSender.send(message);
            log.info("Email has been sent.");
        } catch (MailException e) {
            log.error("Failed to process email sending: {}", e.getMessage());
        }
    }

}
