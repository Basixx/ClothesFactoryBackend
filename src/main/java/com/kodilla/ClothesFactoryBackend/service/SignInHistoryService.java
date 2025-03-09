package com.kodilla.ClothesFactoryBackend.service;

import com.kodilla.ClothesFactoryBackend.domain.SignInHistory;
import com.kodilla.ClothesFactoryBackend.repository.SignInHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class SignInHistoryService {

    private final SignInHistoryRepository signInHistoryRepository;

    public List<SignInHistory> getAllSignInHistory() {
        return signInHistoryRepository.findAll();
    }

}
