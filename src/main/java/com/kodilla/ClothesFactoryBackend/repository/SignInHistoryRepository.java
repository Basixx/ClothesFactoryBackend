package com.kodilla.ClothesFactoryBackend.repository;

import com.kodilla.ClothesFactoryBackend.domain.SignInHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SignInHistoryRepository extends CrudRepository<SignInHistory, Long> {

    @Override
    List<SignInHistory> findAll();

}
