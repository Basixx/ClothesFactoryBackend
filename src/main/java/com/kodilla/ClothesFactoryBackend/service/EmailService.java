package com.kodilla.ClothesFactoryBackend.service;

import com.kodilla.ClothesFactoryBackend.config.AdminConfig;
import com.kodilla.ClothesFactoryBackend.mail.Mail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final AdminConfig adminConfig;

    public void send(final Mail mail) {
        log.info("Starting email preparation...");

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(message, true);

                helper.setFrom(adminConfig.getShopEmail());

                helper.setSubject(mail.getSubject());
                helper.setTo(mail.getMailTo());
                helper.setText(mail.getMessage(), false);
                javaMailSender.send(message);
            log.info("Email has been sent.");

        } catch (MailException e) {
            log.error("Failed to process email sending: " + e.getMessage(), e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}