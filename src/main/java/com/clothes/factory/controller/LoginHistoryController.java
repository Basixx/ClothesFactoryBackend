package com.clothes.factory.controller;

import com.clothes.factory.domain.LoginHistoryDto;
import com.clothes.factory.facade.LoginHistoryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/loginHistory")
@RequiredArgsConstructor
public class LoginHistoryController {

    private final LoginHistoryFacade loginHistoryFacade;

    @GetMapping
    @ResponseStatus(OK)
    public List<LoginHistoryDto> getAllLoginHistory() {
        return loginHistoryFacade.getAllLoginHistory();
    }

}
