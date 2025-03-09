package com.clothes.factory.service;

import com.clothes.factory.config.AdminConfig;
import com.clothes.factory.mail.Mail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


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
            log.error("Failed to process email sending: {}", e.getMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
