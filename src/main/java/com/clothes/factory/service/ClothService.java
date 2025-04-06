package com.clothes.factory.service;

import com.clothes.factory.auxiliary.Prices;
import com.clothes.factory.domain.Cart;
import com.clothes.factory.domain.Cloth;
import com.clothes.factory.domain.Order;
import com.clothes.factory.domain.User;
import com.clothes.factory.exception.api.ProfanityCheckFailedException;
import com.clothes.factory.exception.cloth.ClothNotFoundException;
import com.clothes.factory.exception.cloth.ClothPrintContainsBadWordsException;
import com.clothes.factory.exception.cloth.ClothWithQuantityZeroException;
import com.clothes.factory.exception.order.OrderNotFoundException;
import com.clothes.factory.exception.user.UserNotFoundException;
import com.clothes.factory.repository.ClothRepository;
import com.clothes.factory.repository.OrderRepository;
import com.clothes.factory.repository.UserRepository;
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
    private final Prices prices;

    public List<Cloth> getAllClothes(int page, int size) {
        return clothRepository.findAll(page, size);
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

    public Cloth createCloth(final Cloth cloth) throws ProfanityCheckFailedException, ClothPrintContainsBadWordsException, ClothWithQuantityZeroException {

        boolean containsBadWords = badWordsService.containsBadWords(cloth.getPrint());
        if (cloth.getQuantity() == 0) {
            throw new ClothWithQuantityZeroException();
        } else if (!containsBadWords) {
            return clothRepository.save(cloth);
        } else {
            throw new ClothPrintContainsBadWordsException();
        }
    }

    public Cloth editCloth(final Long id, final Cloth cloth) throws ClothNotFoundException, ProfanityCheckFailedException, ClothPrintContainsBadWordsException {
        Cloth clothFromDb = clothRepository.findById(id).orElseThrow(ClothNotFoundException::new);
        boolean containsBadWords = badWordsService.containsBadWords(cloth.getPrint());

        if (!containsBadWords) {
            clothFromDb.setFashion(cloth.getFashion());
            clothFromDb.setColor(cloth.getColor());
            clothFromDb.setPrint(cloth.getPrint());
            clothFromDb.setFont(cloth.getFont());
            clothFromDb.setPrintColor(cloth.getPrintColor());
            clothFromDb.setSize(cloth.getSize());
            clothFromDb.setQuantity(cloth.getQuantity());
            clothFromDb.setPrice(prices.findClothPrice(clothFromDb.getFashion()).multiply(BigDecimal.valueOf(clothFromDb.getQuantity())));
            BigDecimal cartPrice = clothFromDb.getCart().getClothesList().stream().map(Cloth::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            clothFromDb.getCart().setTotalPrice(cartPrice);
            return clothFromDb;
        } else {
            throw new ClothPrintContainsBadWordsException();
        }
    }

}
