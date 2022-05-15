package com.kodilla.ClothesFactoryBackend.mapper;

import com.kodilla.ClothesFactoryBackend.domain.LoginHistory;
import com.kodilla.ClothesFactoryBackend.domain.LoginHistoryDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class LoginHistoryMapperTestSuite {

    @InjectMocks
    private LoginHistoryMapper loginHistoryMapper;

    @Test
    public void testMapToLoginHistoryDto() {
        //Given
        LoginHistory loginHistory = createLogin1();

        //When
        LoginHistoryDto loginHistoryDto = loginHistoryMapper.mapToLoginHistoryDto(loginHistory);

        //Then
        assertEquals(LocalDateTime.of(2022, 5, 15,13, 15 ), loginHistoryDto.getLoginTime());
        assertEquals("test@mail.com", loginHistoryDto.getUserMail());
        assertTrue(loginHistoryDto.isSucceed());
    }

    @Test
    public void testMapToLoginHistoryDtoList() {
        //Given
        List<LoginHistory> loginHistoryList = new ArrayList<>();
        LoginHistory loginHistory1 = createLogin1();
        LoginHistory loginHistory2 = createLogin2();
        loginHistoryList.add(loginHistory1);
        loginHistoryList.add(loginHistory2);

        //When
        List<LoginHistoryDto> loginHistoryDtoList = loginHistoryMapper.mapToLoginHistoryDtoList(loginHistoryList);

        //Then
        assertEquals(2, loginHistoryDtoList.size());

        assertEquals(LocalDateTime.of(2022, 5, 15,13, 15 ), loginHistoryDtoList.get(0).getLoginTime());
        assertEquals("test@mail.com", loginHistoryDtoList.get(0).getUserMail());
        assertTrue(loginHistoryDtoList.get(0).isSucceed());

        assertEquals(LocalDateTime.of(2022, 5, 14,12, 3 ), loginHistoryDtoList.get(1).getLoginTime());
        assertEquals("test2@mail.com", loginHistoryDtoList.get(1).getUserMail());
        assertFalse(loginHistoryDtoList.get(1).isSucceed());
    }

    private LoginHistory createLogin1() {
        return LoginHistory.builder()
                .loginTime(LocalDateTime.of(2022, 5, 15,13, 15 ))
                .userMail("test@mail.com")
                .succeed(true)
                .build();
    }

    private LoginHistory createLogin2() {
        return LoginHistory.builder()
                .loginTime(LocalDateTime.of(2022, 5, 14,12, 3 ))
                .userMail("test2@mail.com")
                .succeed(false)
                .build();
    }
}
