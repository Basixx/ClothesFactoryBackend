package com.kodilla.ClothesFactoryBackend.service;

import com.kodilla.ClothesFactoryBackend.domain.User;
import com.kodilla.ClothesFactoryBackend.exception.*;
import com.kodilla.ClothesFactoryBackend.mail.UserMailCreator;
import com.kodilla.ClothesFactoryBackend.object_mother.UserMother;
import com.kodilla.ClothesFactoryBackend.repository.CartRepository;
import com.kodilla.ClothesFactoryBackend.repository.LoginHistoryRepository;
import com.kodilla.ClothesFactoryBackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTestSuite {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private LoginHistoryRepository loginHistoryRepository;
    @Mock
    private EmailVerificationService emailVerificationService;
    @Mock
    private EmailService emailService;
    @Mock
    private UserMailCreator userMailCreator;

    @Test
    void testGetAllUsers() {
        //Given
        User user1 = UserMother.createUser1();
        User user2 = UserMother.createUser2();
        List<User> userList = List.of(user1, user2);
        when(userRepository.findAll()).thenReturn(userList);

        //When
        List<User> resultList = userService.getAllUsers();

        //Then
        assertEquals(2, resultList.size());
        assertTrue(resultList.contains(user1));
        assertTrue(resultList.contains(user2));
    }

    @Test
    void testGetUser() throws UserNotFoundException {
        //Given
        User user = UserMother.createUser1();
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

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
        User user = UserMother.createUser1();
        user.setCart(null);
        when(userRepository.existsUserByEmailAddress(anyString())).thenReturn(false);
        when(emailVerificationService.emailExists(anyString())).thenReturn(true);
        when(userRepository.save(any())).thenReturn(user);

        //When
        User resultUser = userService.createUser(user);

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
        assertNotNull(resultUser.getCart());
        assertEquals(0, resultUser.getCart().getClothesList().size());
    }

    @Test
    void testEditUser() throws UserNotFoundException {
        //Given
        User user = UserMother.createUser1();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User editedUser = UserMother.createUser2();
        editedUser.setId(6L);

        //When
        User resultUser = userService.editUser(6L, editedUser);

        //Then
        assertEquals("Mike", resultUser.getName());
        assertEquals("Wazowski", resultUser.getSurname());
        assertEquals("222222222", resultUser.getPhoneNumber());
        assertEquals("john@smith.com", resultUser.getEmailAddress());
        assertEquals("password2", resultUser.getPassword());
        assertEquals("Marszalkowska", resultUser.getStreet());
        assertEquals("1/2", resultUser.getStreetAndApartmentNumber());
        assertEquals("Warsaw", resultUser.getCity());
        assertEquals("00-111", resultUser.getPostCode());
        assertEquals(0, resultUser.getOrdersList().size());
    }

    @Test
    void testAuthenticateUserCorrectly() throws UserEmailNotFoundException, WrongPasswordException {
        //Given
        User user = UserMother.createUser1();
        when(userRepository.findByEmailAddress(anyString())).thenReturn(Optional.of(user));

        //When
        User resultUser = userService.authenticateUser("john@smith.com", "password1");

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
    }
}