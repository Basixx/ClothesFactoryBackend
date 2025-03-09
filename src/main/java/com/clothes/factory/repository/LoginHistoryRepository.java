package com.clothes.factory.repository;

import com.clothes.factory.domain.LoginHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginHistoryRepository extends CrudRepository<LoginHistory, Long> {

    @NonNull
    @Override
    List<LoginHistory> findAll();

}
