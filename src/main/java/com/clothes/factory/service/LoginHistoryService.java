package com.clothes.factory.service;

import com.clothes.factory.domain.LoginHistory;
import com.clothes.factory.repository.LoginHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class LoginHistoryService {

    private final LoginHistoryRepository loginHistoryRepository;

    public List<LoginHistory> getAllLoginHistory() {
        return loginHistoryRepository.findAll();
    }

}
