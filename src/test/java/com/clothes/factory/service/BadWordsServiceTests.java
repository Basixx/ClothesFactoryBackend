package com.clothes.factory.service;

import com.clothes.factory.client.bad_words_api.BadWordsApiClient;
import com.clothes.factory.domain.BadWordsClientDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BadWordsServiceTests {

    @InjectMocks
    private BadWordsService badWordsService;

    @Mock
    private BadWordsApiClient badWordsApiClient;

    @Test
    void testContainsBadWords() throws Exception {
        //Given
        BadWordsClientDto badWordsClientDto2 = new BadWordsClientDto();
        badWordsClientDto2.setBadWordsTotal(3);
        when(badWordsApiClient.checkProfanity("bad word"))
                .thenReturn(badWordsClientDto2);

        //When
        boolean contains2 = badWordsService.containsBadWords("bad word");

        //Then
        assertTrue(contains2);
    }

    @Test
    void testContainsNoBadWords() throws Exception {
        //Given
        BadWordsClientDto badWordsClientDto1 = new BadWordsClientDto();
        badWordsClientDto1.setBadWordsTotal(0);
        when(badWordsApiClient.checkProfanity("nice word"))
                .thenReturn(badWordsClientDto1);

        //When
        boolean contains1 = badWordsService.containsBadWords("nice word");

        //Then
        assertFalse(contains1);
    }

}
