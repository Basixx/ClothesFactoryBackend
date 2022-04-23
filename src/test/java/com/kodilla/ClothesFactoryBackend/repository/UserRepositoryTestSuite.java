package com.kodilla.ClothesFactoryBackend.repository;

import com.kodilla.ClothesFactoryBackend.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserRepositoryTestSuite {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        //Given
        User user1 = createUser1();
        User user2 = createUser2();

        //When
        userRepository.save(user1);
        userRepository.save(user2);

        //Then
        assertEquals(2, userRepository.findAll().size());
    }

    @Test
    public void testDeleteUser() {
        //Given
        User user1 = createUser1();
        User user2 = createUser2();
        userRepository.save(user1);
        userRepository.save(user2);
        int sizeBefore = userRepository.findAll().size();
        Long id = user2.getId();

        //When
        userRepository.deleteById(id);
        int sizeAfter = userRepository.findAll().size();

        //Then
        assertEquals(2, sizeBefore);
        assertEquals(1, sizeAfter);
    }

    private User createUser1() {
        return User.builder()
                .name("John")
                .surname("Smith")
                .phoneNumber("111111111")
                .emailAddress("john@smith.com")
                .password("password1")
                .build();
    }
    private User createUser2() {
        return User.builder()
                .name("Mike")
                .surname("Wazowski")
                .phoneNumber("222222222")
                .emailAddress("mike@wazowski.com")
                .password("password2")
                .build();
    }
}
