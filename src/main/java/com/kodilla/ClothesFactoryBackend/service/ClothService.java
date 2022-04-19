package com.kodilla.ClothesFactoryBackend.service;

import com.kodilla.ClothesFactoryBackend.domain.Cart;
import com.kodilla.ClothesFactoryBackend.domain.Cloth;
import com.kodilla.ClothesFactoryBackend.domain.Order;
import com.kodilla.ClothesFactoryBackend.domain.User;
import com.kodilla.ClothesFactoryBackend.exception.ClothNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.OrderNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.UserNotFoundException;
import com.kodilla.ClothesFactoryBackend.repository.ClothRepository;
import com.kodilla.ClothesFactoryBackend.repository.OrderRepository;
import com.kodilla.ClothesFactoryBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClothService {
    private final ClothRepository clothRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

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

    public Cloth createCloth (final Cloth cloth) {
        return clothRepository.save(cloth);
    }

    public Cloth editCloth (final Long id, final Cloth cloth) throws ClothNotFoundException {
        Cloth clothFromDb = clothRepository.findById(id).orElseThrow(ClothNotFoundException::new);
        clothFromDb.setFashion(clothFromDb.getFashion());
        clothFromDb.setColor(cloth.getColor());
        clothFromDb.setPrint(cloth.getPrint());
        clothFromDb.setFont(cloth.getFont());
        clothFromDb.setPrintColor(cloth.getPrintColor());
        return clothRepository.save(clothFromDb);
    }
}