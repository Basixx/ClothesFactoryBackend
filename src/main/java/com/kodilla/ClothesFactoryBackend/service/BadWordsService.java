package com.kodilla.ClothesFactoryBackend.service;

import com.kodilla.ClothesFactoryBackend.client.bad_words_api.BadWordsApiClient;
import com.kodilla.ClothesFactoryBackend.domain.BadWordsClientDto;
import com.kodilla.ClothesFactoryBackend.exception.api.ProfanityCheckFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class BadWordsService {
    private final BadWordsApiClient badWordsClient;

    public boolean containsBadWords(String text) throws ProfanityCheckFailedException {
        BadWordsClientDto badWordsClientDto = badWordsClient.checkProfanity(text);
        int badWordsCount = badWordsClientDto.getBadWordsTotal();

        return badWordsCount != 0;
    }
}