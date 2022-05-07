package com.kodilla.ClothesFactoryBackend.facade;

import com.kodilla.ClothesFactoryBackend.exception.ProfanityCheckFailedException;
import com.kodilla.ClothesFactoryBackend.service.BadWordsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BadWordsFacade {
    private final BadWordsService badWordsService;

    public boolean getProfanityCheck(String text) throws ProfanityCheckFailedException {
        return badWordsService.getBadWordsCount(text);
    }
}
