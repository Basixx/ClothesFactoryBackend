package com.kodilla.ClothesFactoryBackend.service;

import com.kodilla.ClothesFactoryBackend.domain.Cart;
import com.kodilla.ClothesFactoryBackend.domain.LoginHistory;
import com.kodilla.ClothesFactoryBackend.domain.User;
import com.kodilla.ClothesFactoryBackend.exception.*;
import com.kodilla.ClothesFactoryBackend.repository.CartRepository;
import com.kodilla.ClothesFactoryBackend.repository.LoginHistoryRepository;
import com.kodilla.ClothesFactoryBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final LoginHistoryRepository loginHistoryRepository;
    private final EmailVerificationService emailVerificationService;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(final Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User createUser(final User user) throws UserAlreadyExistsException, EmailVerificationFailedException, EmailAddressDoesNotExistException {
        if(userRepository.existsUserByEmailAddress(user.getEmailAddress())) {
            throw new UserAlreadyExistsException();
        } else {
            if(!emailVerificationService.emailExists(user.getEmailAddress())) {
                throw new EmailAddressDoesNotExistException();
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
            loginHistoryRepository.save(LoginHistory.builder().loginTime(LocalDateTime.now()).userMail(userFromDb.getEmailAddress()).succeed(true).build());
            return userFromDb;
        } else {
            loginHistoryRepository.save(LoginHistory.builder().loginTime(LocalDateTime.now()).userMail(userFromDb.getEmailAddress()).succeed(false).build());
            throw new WrongPasswordException();
        }
    }
}