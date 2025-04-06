package com.clothes.factory.repository;

import com.clothes.factory.domain.SignInHistory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SignInHistoryRepository extends JpaRepository<SignInHistory, Long> {

    default List<SignInHistory> findAll(int page, int size) {
        return findAll(PageRequest.of(page, size)).getContent();
    }

}
