package com.kodilla.ClothesFactoryBackend.service;

import com.kodilla.ClothesFactoryBackend.domain.Cart;
import com.kodilla.ClothesFactoryBackend.domain.LoginHistory;
import com.kodilla.ClothesFactoryBackend.domain.SignInHistory;
import com.kodilla.ClothesFactoryBackend.domain.User;
import com.kodilla.ClothesFactoryBackend.exception.email.EmailAddressDoesNotExistException;
import com.kodilla.ClothesFactoryBackend.exception.email.EmailVerificationFailedException;
import com.kodilla.ClothesFactoryBackend.exception.user.UserAlreadyExistsException;
import com.kodilla.ClothesFactoryBackend.exception.user.UserEmailNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.user.UserNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.user.WrongPasswordException;
import com.kodilla.ClothesFactoryBackend.mail.UserMailCreator;
import com.kodilla.ClothesFactoryBackend.repository.CartRepository;
import com.kodilla.ClothesFactoryBackend.repository.LoginHistoryRepository;
import com.kodilla.ClothesFactoryBackend.repository.SignInHistoryRepository;
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
    private final SignInHistoryRepository signInHistoryRepository;
    private final EmailVerificationService emailVerificationService;
    private final EmailService emailService;
    private final UserMailCreator userMailCreator;

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
                emailService.send(userMailCreator.accountCreationMail(user));
                signInHistoryRepository.save(SignInHistory.builder().signInTime(LocalDateTime.now()).userMail(user.getEmailAddress()).userNumber(user.getPhoneNumber()).build());
                return userRepository.save(user);
            }
        }
    }

    public User editUser(final Long id, final User user) throws UserNotFoundException {
        User userFromDb = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userFromDb.setName(user.getName());
        userFromDb.setSurname(user.getSurname());
        userFromDb.setPhoneNumber(user.getPhoneNumber());
        userFromDb.setPassword(user.getPassword());
        userFromDb.setStreet(user.getStreet());
        userFromDb.setStreetAndApartmentNumber(user.getStreetAndApartmentNumber());
        userFromDb.setCity(user.getCity());
        userFromDb.setPostCode(user.getPostCode());
        return userFromDb;
    }

    public void deleteUser(final Long id) throws UserNotFoundException {
        User userFromDb = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        Cart cartFromDb = userFromDb.getCart();
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