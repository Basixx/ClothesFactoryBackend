package com.clothes.factory.facade;

import com.clothes.factory.domain.LoginHistoryDto;
import com.clothes.factory.mapper.LoginHistoryMapper;
import com.clothes.factory.service.LoginHistoryService;
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

    public List<LoginHistoryDto> getAllLoginHistory(int page, int size) {
        return loginHistoryMapper.mapToLoginHistoryDtoList(loginHistoryService.getAllLoginHistory(page, size));
    }

}
