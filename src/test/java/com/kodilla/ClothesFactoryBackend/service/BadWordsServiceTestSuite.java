package com.kodilla.ClothesFactoryBackend.service;

import com.kodilla.ClothesFactoryBackend.client.bad_words_api.BadWordsApiClient;
import com.kodilla.ClothesFactoryBackend.domain.BadWordsClientDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BadWordsServiceTestSuite {

    @InjectMocks
    private BadWordsService badWordsService;

    @Mock
    private BadWordsApiClient badWordsApiClient;

    @Test
    void testContainsBadWords() throws Exception {
        //Given
        BadWordsClientDto badWordsClientDto2 = BadWordsClientDto.builder().badWordsTotal(3).build();
        when(badWordsApiClient.checkProfanity("bad word")).thenReturn(badWordsClientDto2);

        //When
        boolean contains2 = badWordsService.containsBadWords("bad word");

        //Then
        assertTrue(contains2);
    }

    @Test
    void testContainsNoBadWords() throws Exception {
        //Given
        BadWordsClientDto badWordsClientDto1 = BadWordsClientDto.builder().badWordsTotal(0).build();
        when(badWordsApiClient.checkProfanity("nice word")).thenReturn(badWordsClientDto1);

        //When
        boolean contains1 = badWordsService.containsBadWords("nice word");

        //Then
        assertFalse(contains1);
    }
}