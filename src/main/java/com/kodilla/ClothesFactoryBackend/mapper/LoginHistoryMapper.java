package com.kodilla.ClothesFactoryBackend.mapper;

import com.kodilla.ClothesFactoryBackend.domain.LoginHistory;
import com.kodilla.ClothesFactoryBackend.domain.LoginHistoryDto;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

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
                .collect(Collectors.toList());
    }
}