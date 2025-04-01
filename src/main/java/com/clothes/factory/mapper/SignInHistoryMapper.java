package com.clothes.factory.mapper;

import com.clothes.factory.domain.SignInHistory;
import com.clothes.factory.domain.SignInHistoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SignInHistoryMapper {

    public SignInHistoryDto mapToSignInHistoryDto(final SignInHistory signInHistory) {
        return SignInHistoryDto.builder()
                .id(signInHistory.getId())
                .signInTime(signInHistory.getSignInTime())
                .userMail(signInHistory.getUserMail())
                .userNumber(signInHistory.getUserNumber())
                .build();
    }

    public List<SignInHistoryDto> mapToSignInHistoryDtoList(final List<SignInHistory> signInHistories) {
        return signInHistories.stream()
                .map(this::mapToSignInHistoryDto)
                .toList();
    }

}
