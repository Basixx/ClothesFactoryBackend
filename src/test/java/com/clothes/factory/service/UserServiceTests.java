package com.clothes.factory.service;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static com.clothes.factory.object_mother.UserMother.createUser1;
import static com.clothes.factory.object_mother.UserMother.createUser2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {


    private UserService userService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private LoginHistoryRepository loginHistoryRepository;

    @Mock
    private SignInHistoryRepository signInHistoryRepository;

    @Mock
    private EmailVerificationService emailVerificationService;

    @Mock
    private EmailService emailService;

    @Mock
    private UserMailCreator userMailCreator;

    @BeforeEach
    void setUp() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        userService = new UserService(
                userRepository,
                cartRepository,
                loginHistoryRepository,
                signInHistoryRepository,
                emailVerificationService,
                emailService,
                userMailCreator,
                bCryptPasswordEncoder
        );
    }

    @Test
    void testGetAllUsers() {
        //Given
        User user1 = createUser1();
        User user2 = createUser2();
        List<User> userList = List.of(user1, user2);
        when(userRepository.findAll(0, 10))
                .thenReturn(userList);

        //When
        List<User> resultList = userService.getAllUsers(0, 10);

        //Then
        assertEquals(2, resultList.size());
        assertTrue(resultList.contains(user1));
        assertTrue(resultList.contains(user2));
    }

    @Test
    void testGetUser() throws UserNotFoundException {
        //Given
        User user = createUser1();
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(user));

        //When
        User resultUser = userService.getUser(6L);

        //Then
        assertEquals("John", resultUser.getName());
        assertEquals("Smith", resultUser.getSurname());
        assertEquals("111111111", resultUser.getPhoneNumber());
        assertEquals("john@smith.com", resultUser.getEmailAddress());
        assertEquals("password1", resultUser.getPassword());
        assertEquals("Marszalkowska", resultUser.getStreet());
        assertEquals("1/2", resultUser.getStreetAndApartmentNumber());
        assertEquals("Warsaw", resultUser.getCity());
        assertEquals("00-111", resultUser.getPostCode());
        assertEquals(0, resultUser.getOrdersList().size());
    }

    @Test
    void testCreateUser() throws EmailVerificationFailedException, UserAlreadyExistsException, EmailAddressDoesNotExistException {
        //Given
        User user = createUser1();
        user.setCart(null);
        when(userRepository.existsUserByEmailAddress(anyString()))
                .thenReturn(false);
        when(emailVerificationService.emailExists(anyString()))
                .thenReturn(true);
        when(userRepository.save(any()))
                .thenReturn(user);

        //When
        User resultUser = userService.createUser(user);

        //Then
        assertEquals("John", resultUser.getName());
        assertEquals("Smith", resultUser.getSurname());
        assertEquals("111111111", resultUser.getPhoneNumber());
        assertEquals("john@smith.com", resultUser.getEmailAddress());
        assertTrue(bCryptPasswordEncoder.matches("password1", resultUser.getPassword()));
        assertEquals("Marszalkowska", resultUser.getStreet());
        assertEquals("1/2", resultUser.getStreetAndApartmentNumber());
        assertEquals("Warsaw", resultUser.getCity());
        assertEquals("00-111", resultUser.getPostCode());
        assertEquals(0, resultUser.getOrdersList().size());
        assertNotNull(resultUser.getCart());
        assertEquals(0, resultUser.getCart().getClothesList().size());
    }

    @Test
    void testEditUser() throws UserNotFoundException {
        //Given
        User user = createUser1();
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));

        User editedUser = createUser2();
        editedUser.setId(6L);

        //When
        User resultUser = userService.editUser(6L, editedUser);

        //Then
        assertEquals("Mike", resultUser.getName());
        assertEquals("Wazowski", resultUser.getSurname());
        assertEquals("222222222", resultUser.getPhoneNumber());
        assertEquals("john@smith.com", resultUser.getEmailAddress());
        assertTrue(bCryptPasswordEncoder.matches("password2", resultUser.getPassword()));
        assertEquals("Polinezyjska", resultUser.getStreet());
        assertEquals("4/10", resultUser.getStreetAndApartmentNumber());
        assertEquals("Cracow", resultUser.getCity());
        assertEquals("00-222", resultUser.getPostCode());
        assertEquals(0, resultUser.getOrdersList().size());
    }

    @Test
    void testAuthenticateUserCorrectly() throws UserEmailNotFoundException, WrongPasswordException {
        //Given
        User user = createUser1();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        when(userRepository.findByEmailAddress(anyString()))
                .thenReturn(Optional.of(user));

        //When
        User resultUser = userService.authenticateUser("john@smith.com", "password1");

        //Then
        assertEquals("John", resultUser.getName());
        assertEquals("Smith", resultUser.getSurname());
        assertEquals("111111111", resultUser.getPhoneNumber());
        assertEquals("john@smith.com", resultUser.getEmailAddress());
        assertTrue(bCryptPasswordEncoder.matches("password1", resultUser.getPassword()));
        assertEquals("Marszalkowska", resultUser.getStreet());
        assertEquals("1/2", resultUser.getStreetAndApartmentNumber());
        assertEquals("Warsaw", resultUser.getCity());
        assertEquals("00-111", resultUser.getPostCode());
    }

}
