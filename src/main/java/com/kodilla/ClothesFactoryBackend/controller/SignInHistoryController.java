package com.kodilla.ClothesFactoryBackend.controller;

import com.kodilla.ClothesFactoryBackend.domain.SignInHistoryDto;
import com.kodilla.ClothesFactoryBackend.facade.SignInHistoryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/v1/signInHistory")
@RequiredArgsConstructor
public class SignInHistoryController {
    private final SignInHistoryFacade signInHistoryFacade;

    @GetMapping
    public ResponseEntity<List<SignInHistoryDto>> getAllSignInHistory() {
        return ResponseEntity.ok(signInHistoryFacade.getAllSignInHistory());
    }
}