package com.clothes.factory.mapper;

import com.clothes.factory.domain.LoginHistory;
import com.clothes.factory.domain.LoginHistoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginHistoryMapper {

    public LoginHistoryDto mapToLoginHistoryDto(final LoginHistory loginHistory) {
        return LoginHistoryDto.builder()
                .id(loginHistory.getId())
                .loginTime(loginHistory.getLoginTime())
                .userMail(loginHistory.getUserMail())
                .succeed(loginHistory.isSucceed())
                .build();
    }

    public List<LoginHistoryDto> mapToLoginHistoryDtoList(final List<LoginHistory> loginHistories) {
        return loginHistories.stream()
                .map(this::mapToLoginHistoryDto)
                .toList();
    }

}
