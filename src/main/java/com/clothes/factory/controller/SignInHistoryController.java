package com.clothes.factory.controller;

import com.clothes.factory.domain.SignInHistoryDto;
import com.clothes.factory.facade.SignInHistoryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/signInHistory")
@RequiredArgsConstructor
public class SignInHistoryController {

    private final SignInHistoryFacade signInHistoryFacade;

    @GetMapping
    public List<SignInHistoryDto> getAllSignInHistory(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        return signInHistoryFacade.getAllSignInHistory(page, size);
    }

}
