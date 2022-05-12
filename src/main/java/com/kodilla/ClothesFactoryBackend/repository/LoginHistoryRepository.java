package com.kodilla.ClothesFactoryBackend.repository;

import com.kodilla.ClothesFactoryBackend.domain.LoginHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LoginHistoryRepository extends CrudRepository<LoginHistory, Long> {

    @Override
    List<LoginHistory> findAll();
}