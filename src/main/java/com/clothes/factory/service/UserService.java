package com.clothes.factory.service;

import com.clothes.factory.domain.Cart;
import com.clothes.factory.domain.LoginHistory;
import com.clothes.factory.domain.SignInHistory;
import com.clothes.factory.domain.User;
import com.clothes.factory.exception.email.EmailAddressDoesNotExistException;
import com.clothes.factory.exception.email.EmailVerificationFailedException;
import com.clothes.factory.exception.user.UserAlreadyExistsException;
import com.clothes.factory.exception.user.UserEmailNotFoundException;
import com.clothes.factory.exception.user.UserNotFoundException;
import com.clothes.factory.exception.user.WrongPasswordException;
import com.clothes.factory.mail.UserMailCreator;
import com.clothes.factory.repository.CartRepository;
import com.clothes.factory.repository.LoginHistoryRepository;
import com.clothes.factory.repository.SignInHistoryRepository;
import com.clothes.factory.repository.UserRepository;
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
        if (userRepository.existsUserByEmailAddress(user.getEmailAddress())) {
            throw new UserAlreadyExistsException();
        } else {
            if (!emailVerificationService.emailExists(user.getEmailAddress())) {
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

        if (password.equals(userFromDb.getPassword())) {
            loginHistoryRepository.save(LoginHistory.builder().loginTime(LocalDateTime.now()).userMail(userFromDb.getEmailAddress()).succeed(true).build());
            return userFromDb;
        } else {
            loginHistoryRepository.save(LoginHistory.builder().loginTime(LocalDateTime.now()).userMail(userFromDb.getEmailAddress()).succeed(false).build());
            throw new WrongPasswordException();
        }
    }

}
