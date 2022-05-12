package com.kodilla.ClothesFactoryBackend.facade;

import com.kodilla.ClothesFactoryBackend.domain.LoginHistoryDto;
import com.kodilla.ClothesFactoryBackend.mapper.LoginHistoryMapper;
import com.kodilla.ClothesFactoryBackend.service.LoginHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginHistoryFacade {
    private final LoginHistoryService loginHistoryService;
    private final LoginHistoryMapper loginHistoryMapper;

    public List<LoginHistoryDto> getAllLoginHistory() {
        return loginHistoryMapper.mapToLoginHistoryDtoList(loginHistoryService.getAllLoginHistory());
    }
}