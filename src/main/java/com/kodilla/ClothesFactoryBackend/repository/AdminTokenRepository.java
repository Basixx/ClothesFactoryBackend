package com.kodilla.ClothesFactoryBackend.repository;

import com.kodilla.ClothesFactoryBackend.domain.AdminToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminTokenRepository extends CrudRepository<AdminToken, Long> {

    Boolean existsByToken(String token);
}
