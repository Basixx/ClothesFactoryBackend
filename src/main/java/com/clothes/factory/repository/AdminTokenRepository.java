package com.clothes.factory.repository;

import com.clothes.factory.domain.AdminToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminTokenRepository extends CrudRepository<AdminToken, Long> {

    Boolean existsByToken(String token);

    @NonNull
    @Override
    List<AdminToken> findAll();

}
