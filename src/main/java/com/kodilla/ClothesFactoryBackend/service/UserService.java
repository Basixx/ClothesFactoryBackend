package com.kodilla.ClothesFactoryBackend.service;

import com.kodilla.ClothesFactoryBackend.domain.Cart;
import com.kodilla.ClothesFactoryBackend.domain.User;
import com.kodilla.ClothesFactoryBackend.exception.UserAlreadyExistsException;
import com.kodilla.ClothesFactoryBackend.exception.UserNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.WrongPasswordException;
import com.kodilla.ClothesFactoryBackend.exception.UserEmailNotFoundException;
import com.kodilla.ClothesFactoryBackend.repository.CartRepository;
import com.kodilla.ClothesFactoryBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(final Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User createUser(final User user) throws UserAlreadyExistsException {
        if(userRepository.existsUserByEmailAddress(user.getEmailAddress())) {
            throw new UserAlreadyExistsException();
        } else {
            Cart userCart = Cart.builder()
                    .totalPrice(BigDecimal.ZERO)
                    .clothesList(new ArrayList<>())
                    .build();
            user.setCart(userCart);
            user.setOrdersList(new ArrayList<>());
            cartRepository.save(userCart);
            return userRepository.save(user);
        }
    }

    public User editUser(final Long id, final User user) throws UserNotFoundException {
        User userFromDb = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userFromDb.setName(user.getName());
        userFromDb.setSurname(user.getSurname());
        userFromDb.setPhoneNumber(user.getPhoneNumber());
        userFromDb.setEmailAddress(user.getEmailAddress());
        userFromDb.setPassword(user.getPassword());
        return userFromDb;
    }

    public void deleteUser(final Long id) {
        Cart cartFromDb = userRepository.findById(id).get().getCart();
        cartRepository.deleteById(cartFromDb.getId());
        userRepository.deleteById(id);
    }

    public User authenticateUser(final String email, final String password) throws UserEmailNotFoundException, WrongPasswordException {
        User userFromDb = userRepository.findByEmailAddress(email).orElseThrow(UserEmailNotFoundException::new);

        if(password.equals(userFromDb.getPassword())) {
            return userFromDb;
        } else {
            throw new WrongPasswordException();
        }


    }
}