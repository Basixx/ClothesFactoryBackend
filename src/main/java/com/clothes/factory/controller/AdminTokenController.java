package com.clothes.factory.controller;

import com.clothes.factory.domain.AdminTokenDto;
import com.clothes.factory.facade.AdminTokenFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/token")
@RequiredArgsConstructor
public class AdminTokenController {

    private final AdminTokenFacade adminTokenFacade;

    @GetMapping(value = "/{token}")
    public ResponseEntity<Boolean> existsToken(@PathVariable String token) {
        return ResponseEntity.ok(adminTokenFacade.existsByToken(token));
    }

    @PostMapping
    public ResponseEntity<AdminTokenDto> createToken() {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminTokenFacade.createToken());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTokens() {
        adminTokenFacade.deleteAllTokens();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
