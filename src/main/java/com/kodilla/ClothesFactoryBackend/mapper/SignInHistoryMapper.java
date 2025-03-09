package com.kodilla.ClothesFactoryBackend.mapper;

import com.kodilla.ClothesFactoryBackend.domain.SignInHistory;
import com.kodilla.ClothesFactoryBackend.domain.SignInHistoryDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
                .collect(Collectors.toList());
    }

}
