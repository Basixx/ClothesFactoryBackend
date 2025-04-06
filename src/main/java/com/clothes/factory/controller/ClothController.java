package com.clothes.factory.controller;

import com.clothes.factory.domain.ClothDto;
import com.clothes.factory.exception.api.ProfanityCheckFailedException;
import com.clothes.factory.exception.cloth.ClothNotFoundException;
import com.clothes.factory.exception.cloth.ClothPrintContainsBadWordsException;
import com.clothes.factory.exception.cloth.ClothWithQuantityZeroException;
import com.clothes.factory.exception.order.OrderNotFoundException;
import com.clothes.factory.exception.user.UserNotFoundException;
import com.clothes.factory.facade.ClothFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class ClothController {

    private final ClothFacade clothFacade;

    @GetMapping("/clothes")
    @ResponseStatus(OK)
    public List<ClothDto> getAllClothes(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        return clothFacade.getAllClothes(page, size);
    }

    @GetMapping(value = "/users/{userId}/cart/clothes")
    @ResponseStatus(OK)
    public List<ClothDto> getClothesFromUserCart(@PathVariable Long userId) throws UserNotFoundException {
        return clothFacade.getClothesFromUserCart(userId);
    }

    @GetMapping(value = "/orders/{orderId}/clothes")
    @ResponseStatus(OK)
    public List<ClothDto> getClothesFromOrder(@PathVariable Long orderId) throws OrderNotFoundException {
        return clothFacade.getClothesFromOrder(orderId);
    }

    @PostMapping("/clothes")
    @ResponseStatus(CREATED)
    public ClothDto createCloth(@RequestBody ClothDto clothDto)
            throws ProfanityCheckFailedException,
            ClothPrintContainsBadWordsException,
            ClothWithQuantityZeroException {
        return clothFacade.createCloth(clothDto);
    }

    @PutMapping("/clothes/{id}")
    @ResponseStatus(OK)
    public ClothDto updateCloth(@PathVariable Long id, @RequestBody ClothDto clothDto)
            throws ClothNotFoundException,
            ProfanityCheckFailedException,
            ClothPrintContainsBadWordsException {
        return clothFacade.updateCloth(id, clothDto);
    }

}
