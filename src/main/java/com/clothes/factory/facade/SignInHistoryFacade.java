package com.clothes.factory.facade;

import com.clothes.factory.domain.SignInHistoryDto;
import com.clothes.factory.mapper.SignInHistoryMapper;
import com.clothes.factory.service.SignInHistoryService;
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
