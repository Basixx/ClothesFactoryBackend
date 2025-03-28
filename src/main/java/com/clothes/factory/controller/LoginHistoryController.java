package com.clothes.factory.controller;

import com.clothes.factory.domain.LoginHistoryDto;
import com.clothes.factory.facade.LoginHistoryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/loginHistory")
@RequiredArgsConstructor
public class LoginHistoryController {

    private final LoginHistoryFacade loginHistoryFacade;

    @GetMapping
    public ResponseEntity<List<LoginHistoryDto>> getAllLoginHistory() {
        return ResponseEntity.ok(loginHistoryFacade.getAllLoginHistory());
    }

}
