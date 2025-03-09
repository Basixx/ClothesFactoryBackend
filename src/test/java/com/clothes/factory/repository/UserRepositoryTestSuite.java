package com.clothes.factory.repository;

import com.clothes.factory.domain.User;
import com.clothes.factory.object_mother.UserMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class UserRepositoryTestSuite {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveUser() {
        //Given
        User user1 = UserMother.createUser1();
        User user2 = UserMother.createUser2();

        user1.setId(null);
        user2.setId(null);

        //When
        userRepository.save(user1);
        userRepository.save(user2);

        //Then
        assertEquals(2, userRepository.findAll().size());
    }

    @Test
    void testDeleteUser() {
        //Given
        User user1 = UserMother.createUser1();
        User user2 = UserMother.createUser2();

        user1.setId(null);
        user2.setId(null);

        userRepository.save(user1);
        userRepository.save(user2);
        Long id = userRepository.findAll()
                .stream()
                .map(User::getId)
                .findFirst()
                .orElseThrow();

        //When
        userRepository.deleteById(id);
        int userCount = userRepository.findAll().size();

        //Then
        assertEquals(1, userCount);
    }
}
