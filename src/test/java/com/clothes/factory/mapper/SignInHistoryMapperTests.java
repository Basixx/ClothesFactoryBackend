package com.clothes.factory.mapper;

import com.clothes.factory.domain.SignInHistory;
import com.clothes.factory.domain.SignInHistoryDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class SignInHistoryMapperTests {

    @InjectMocks
    private SignInHistoryMapper signInHistoryMapper;

    @Test
    void testMapToSignInHistoryDto() {
        //Given
        SignInHistory signInHistory = createSignIn1();

        //When
        SignInHistoryDto signInHistoryDto = signInHistoryMapper.mapToSignInHistoryDto(signInHistory);

        //Then
        assertEquals(LocalDateTime.of(2022, 5, 30, 12, 30, 0), signInHistoryDto.getSignInTime());
        assertEquals("test1@mail.com", signInHistoryDto.getUserMail());
        assertEquals("123456789", signInHistoryDto.getUserNumber());
    }

    @Test
    void testMapToSignInHistoryDtoList() {
        //Given
        List<SignInHistory> signInHistoryList = new ArrayList<>();
        SignInHistory signInHistory1 = createSignIn1();
        SignInHistory signInHistory2 = createSignIn2();
        signInHistoryList.add(signInHistory1);
        signInHistoryList.add(signInHistory2);

        //When
        List<SignInHistoryDto> signInHistoryDtoList = signInHistoryMapper.mapToSignInHistoryDtoList(signInHistoryList);

        //Then
        assertEquals(2, signInHistoryDtoList.size());

        assertEquals(LocalDateTime.of(2022, 5, 30, 12, 30, 0), signInHistoryDtoList.getFirst().getSignInTime());
        assertEquals("test1@mail.com", signInHistoryDtoList.getFirst().getUserMail());
        assertEquals("123456789", signInHistoryDtoList.getFirst().getUserNumber());

        assertEquals(LocalDateTime.of(2022, 6, 1, 11, 45, 0), signInHistoryDtoList.get(1).getSignInTime());
        assertEquals("test2@mail.com", signInHistoryDtoList.get(1).getUserMail());
        assertEquals("987654321", signInHistoryDtoList.get(1).getUserNumber());
    }

    private SignInHistory createSignIn1() {
        return SignInHistory.builder()
                .id(1L)
                .signInTime(LocalDateTime.of(2022, 5, 30, 12, 30, 0))
                .userMail("test1@mail.com")
                .userNumber("123456789")
                .build();
    }

    private SignInHistory createSignIn2() {
        return SignInHistory.builder()
                .id(2L)
                .signInTime(LocalDateTime.of(2022, 6, 1, 11, 45, 0))
                .userMail("test2@mail.com")
                .userNumber("987654321")
                .build();
    }

}
