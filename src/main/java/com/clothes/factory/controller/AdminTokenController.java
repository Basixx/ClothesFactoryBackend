package com.clothes.factory.controller;

import com.clothes.factory.domain.AdminTokenDto;
import com.clothes.factory.facade.AdminTokenFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

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
