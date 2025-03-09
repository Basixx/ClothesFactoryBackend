package com.clothes.factory.repository;

import com.clothes.factory.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @NonNull
    @Override
    List<User> findAll();

    Optional<User> findByEmailAddress(String email);

    boolean existsUserByEmailAddress(String email);

}
