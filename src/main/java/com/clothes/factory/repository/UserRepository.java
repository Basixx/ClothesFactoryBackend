package com.clothes.factory.repository;

import com.clothes.factory.domain.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAddress(String email);

    boolean existsUserByEmailAddress(String email);

    default List<User> findAll(int page, int size) {
        return findAll(PageRequest.of(page, size)).getContent();
    }

}
