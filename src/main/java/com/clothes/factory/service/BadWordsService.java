package com.clothes.factory.service;

import com.clothes.factory.client.bad_words_api.BadWordsApiClient;
import com.clothes.factory.domain.BadWordsClientDto;
import com.clothes.factory.exception.api.ProfanityCheckFailedException;
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
