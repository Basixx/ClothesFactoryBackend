package com.kodilla.ClothesFactoryBackend.controller;

import com.kodilla.ClothesFactoryBackend.domain.AdminTokenDto;
import com.kodilla.ClothesFactoryBackend.facade.AdminTokenFacade;
import lombok.RequiredArgsConstructor;
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
        return ResponseEntity.ok(adminTokenFacade.createToken());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTokens() {
        adminTokenFacade.deleteAllTokens();
        return ResponseEntity.ok().build();
    }
}
