package com.kodilla.ClothesFactoryBackend.service;

import com.kodilla.ClothesFactoryBackend.auxiliary.Prices;
import com.kodilla.ClothesFactoryBackend.domain.Cart;
import com.kodilla.ClothesFactoryBackend.domain.Cloth;
import com.kodilla.ClothesFactoryBackend.domain.Order;
import com.kodilla.ClothesFactoryBackend.domain.User;
import com.kodilla.ClothesFactoryBackend.exception.*;
import com.kodilla.ClothesFactoryBackend.repository.ClothRepository;
import com.kodilla.ClothesFactoryBackend.repository.OrderRepository;
import com.kodilla.ClothesFactoryBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ClothService {
    private final ClothRepository clothRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final BadWordsService badWordsService;

    public List<Cloth> getAllClothes() {
        return clothRepository.findAll();
    }

    public List<Cloth> getAllClothesFromUsersCart(final Long userId) throws UserNotFoundException {
        User userFromDb = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Cart cartFromDb = userFromDb.getCart();

        return cartFromDb.getClothesList();
    }

    public List<Cloth> getAllClothesFromOrder(final Long orderId) throws OrderNotFoundException {
        Order orderFromDb = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        return orderFromDb.getClothesList();
    }

    public Cloth createCloth (final Cloth cloth) throws ProfanityCheckFailedException, ClothPrintContainsBadWordsException {

        boolean containsBadWords = badWordsService.containsBadWords(cloth.getPrint());
        if(!containsBadWords){
            return clothRepository.save(cloth);
        } else {
            throw new ClothPrintContainsBadWordsException();
        }

    }

    public Cloth editCloth (final Long id, final Cloth cloth) throws ClothNotFoundException, ProfanityCheckFailedException, ClothPrintContainsBadWordsException {
        Cloth clothFromDb = clothRepository.findById(id).orElseThrow(ClothNotFoundException::new);
        boolean containsBadWords = badWordsService.containsBadWords(cloth.getPrint());

        if(!containsBadWords){
            clothFromDb.setFashion(cloth.getFashion());
            clothFromDb.setColor(cloth.getColor());
            clothFromDb.setPrint(cloth.getPrint());
            clothFromDb.setFont(cloth.getFont());
            clothFromDb.setPrintColor(cloth.getPrintColor());
            clothFromDb.setSize(cloth.getSize());
            clothFromDb.setQuantity(cloth.getQuantity());
            clothFromDb.setPrice(Prices.findClothPrice(clothFromDb.getFashion()).multiply(BigDecimal.valueOf(clothFromDb.getQuantity())));
            BigDecimal cartPrice = clothFromDb.getCart().getClothesList().stream().map(Cloth::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            clothFromDb.getCart().setTotalPrice(cartPrice);
            return clothFromDb;
        } else {
            throw new ClothPrintContainsBadWordsException();
        }
    }
}