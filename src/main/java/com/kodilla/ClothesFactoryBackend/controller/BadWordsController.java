package com.kodilla.ClothesFactoryBackend.controller;

import com.kodilla.ClothesFactoryBackend.exception.api.ProfanityCheckFailedException;
import com.kodilla.ClothesFactoryBackend.facade.BadWordsFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/badWords")
@RequiredArgsConstructor
public class BadWordsController {
    private final BadWordsFacade badWordsFacade;

    @GetMapping
    public ResponseEntity<Boolean> getProfanityCheck(@RequestBody String text) throws ProfanityCheckFailedException {
        return ResponseEntity.ok(badWordsFacade.getProfanityCheck(text));
    }
}