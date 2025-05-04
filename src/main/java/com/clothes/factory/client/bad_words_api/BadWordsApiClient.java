package com.clothes.factory.client.bad_words_api;

import com.clothes.factory.domain.BadWordsClientDto;
import com.clothes.factory.exception.api.ProfanityCheckFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BadWordsApiClient {

    private final RestTemplate restTemplate;

    @Value("${bad.words.api.endpoint}")
    private String badWordsEndpoint;

    @Value("${api.layer.key}")
    private String badWordsKey;

    public BadWordsClientDto checkProfanity(String text) throws ProfanityCheckFailedException {
        BadWordsClientDto profanityResponse = restTemplate.postForObject(
                badWordsURL(),
                text,
                BadWordsClientDto.class
        );
        return Optional.ofNullable(profanityResponse).orElseThrow(ProfanityCheckFailedException::new);
    }

    private URI badWordsURL() {
        return UriComponentsBuilder.fromUriString(badWordsEndpoint)
                .queryParam("censor_character", "x")
                .queryParam("apikey", badWordsKey)
                .build()
                .encode()
                .toUri();
    }

}
