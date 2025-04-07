package com.clothes.factory.service;

import com.clothes.factory.mail.Mail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(
        name = "app.mail.sending.enabled",
        havingValue = "false"
)
public class LoggingEmailService implements EmailService {

    @Override
    public void send(final Mail mail) {
        log.info("Email sending is disabled. Skipping mail with subject: {}", mail.getSubject());
    }

}
