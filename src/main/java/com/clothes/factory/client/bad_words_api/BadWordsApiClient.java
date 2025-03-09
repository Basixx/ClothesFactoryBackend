package com.clothes.factory.client.bad_words_api;

import com.google.gson.Gson;
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

    @Value("${bad.words.api.endpoint:defaultValue}")
    private String badWordsEndpoint;

    @Value("${api.layer.key:defaultValue}")
    private String badWordsKey;

    public BadWordsClientDto checkProfanity(String text) throws ProfanityCheckFailedException {

        Gson gson = new Gson();
        String jsonContent = gson.toJson(text);

        URI url = UriComponentsBuilder.fromUriString(
                        badWordsEndpoint)
                .queryParam("censor_character", "x")
                .queryParam("apikey", badWordsKey)
                .build()
                .encode()
                .toUri();

        BadWordsClientDto profanityResponse = restTemplate.postForObject(
                url,
                jsonContent,
                BadWordsClientDto.class
        );
        return Optional.ofNullable(profanityResponse).orElseThrow(ProfanityCheckFailedException::new);
    }

}
