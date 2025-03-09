package com.clothes.factory.mapper;

import com.clothes.factory.domain.LoginHistory;
import com.clothes.factory.domain.LoginHistoryDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LoginHistoryMapperTests {

    @InjectMocks
    private LoginHistoryMapper loginHistoryMapper;

    @Test
    void testMapToLoginHistoryDto() {
        //Given
        LoginHistory loginHistory = createLogin1();

        //When
        LoginHistoryDto loginHistoryDto = loginHistoryMapper.mapToLoginHistoryDto(loginHistory);

        //Then
        assertEquals(LocalDateTime.of(2022, 5, 15, 13, 15), loginHistoryDto.getLoginTime());
        assertEquals("test@mail.com", loginHistoryDto.getUserMail());
        assertTrue(loginHistoryDto.isSucceed());
    }

    @Test
    void testMapToLoginHistoryDtoList() {
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

        assertEquals(LocalDateTime.of(2022, 5, 15, 13, 15), loginHistoryDtoList.getFirst().getLoginTime());
        assertEquals("test@mail.com", loginHistoryDtoList.getFirst().getUserMail());
        assertTrue(loginHistoryDtoList.getFirst().isSucceed());

        assertEquals(LocalDateTime.of(2022, 5, 14, 12, 3), loginHistoryDtoList.get(1).getLoginTime());
        assertEquals("test2@mail.com", loginHistoryDtoList.get(1).getUserMail());
        assertFalse(loginHistoryDtoList.get(1).isSucceed());
    }

    private LoginHistory createLogin1() {
        return LoginHistory.builder()
                .loginTime(LocalDateTime.of(2022, 5, 15, 13, 15))
                .userMail("test@mail.com")
                .succeed(true)
                .build();
    }

    private LoginHistory createLogin2() {
        return LoginHistory.builder()
                .loginTime(LocalDateTime.of(2022, 5, 14, 12, 3))
                .userMail("test2@mail.com")
                .succeed(false)
                .build();
    }

}
