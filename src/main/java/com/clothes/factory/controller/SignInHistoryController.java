package com.clothes.factory.controller;

import com.clothes.factory.domain.SignInHistoryDto;
import com.clothes.factory.facade.SignInHistoryFacade;
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
