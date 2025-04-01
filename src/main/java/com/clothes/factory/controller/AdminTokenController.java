package com.clothes.factory.controller;

import com.clothes.factory.domain.AdminTokenDto;
import com.clothes.factory.facade.AdminTokenFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/tokens")
@RequiredArgsConstructor
public class AdminTokenController {

    private final AdminTokenFacade adminTokenFacade;

    @GetMapping(value = "/{token}")
    @ResponseStatus(OK)
    public boolean existsToken(@PathVariable String token) {
        return adminTokenFacade.existsByToken(token);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public AdminTokenDto createToken() {
        return adminTokenFacade.createToken();
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    public void deleteTokens() {
        adminTokenFacade.deleteAllTokens();
    }

}
