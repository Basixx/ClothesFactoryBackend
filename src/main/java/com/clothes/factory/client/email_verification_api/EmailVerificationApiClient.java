package com.clothes.factory.client.email_verification_api;

import com.clothes.factory.domain.EmailVerificationDto;
import com.clothes.factory.exception.email.EmailVerificationFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmailVerificationApiClient {

    private final RestTemplate restTemplate;

    @Value("${whoisxml.api.endpoint}")
    private String emailCheckEndpoint;

    @Value("${whoisxml.api.key}")
    private String emailCheckKey;

    public EmailVerificationDto checkEmail(String email) throws EmailVerificationFailedException {
        EmailVerificationDto emailCheckResponse = restTemplate.getForObject(
                emailCheckURL(email),
                EmailVerificationDto.class
        );
        return Optional.ofNullable(emailCheckResponse).orElseThrow(EmailVerificationFailedException::new);
    }

    private URI emailCheckURL(String email) {
        return emailCheckUri()
                .queryParam("emailAddress", email)
                .build()
                .encode()
                .toUri();
    }

    private UriComponentsBuilder emailCheckUri() {
        return UriComponentsBuilder.fromUriString(emailCheckEndpoint)
                .queryParam("apiKey", emailCheckKey);
    }

}
