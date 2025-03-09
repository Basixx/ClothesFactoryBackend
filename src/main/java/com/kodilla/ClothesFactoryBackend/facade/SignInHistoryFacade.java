package com.kodilla.ClothesFactoryBackend.facade;

import com.kodilla.ClothesFactoryBackend.domain.SignInHistoryDto;
import com.kodilla.ClothesFactoryBackend.mapper.SignInHistoryMapper;
import com.kodilla.ClothesFactoryBackend.service.SignInHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SignInHistoryFacade {

    private final SignInHistoryService signInHistoryService;
    private final SignInHistoryMapper signInHistoryMapper;

    public List<SignInHistoryDto> getAllSignInHistory() {
        return signInHistoryMapper.mapToSignInHistoryDtoList(signInHistoryService.getAllSignInHistory());
    }

}
