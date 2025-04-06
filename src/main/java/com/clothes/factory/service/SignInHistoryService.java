package com.clothes.factory.service;

import com.clothes.factory.domain.SignInHistory;
import com.clothes.factory.repository.SignInHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class SignInHistoryService {

    private final SignInHistoryRepository signInHistoryRepository;

    public List<SignInHistory> getAllSignInHistory(int page, int size) {
        return signInHistoryRepository.findAll(page, size);
    }

}
