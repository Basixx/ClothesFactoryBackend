package com.clothes.factory.repository;

import com.clothes.factory.domain.LoginHistory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {

    default List<LoginHistory> findAll(int page, int size) {
        return findAll(PageRequest.of(page, size)).getContent();
    }

}
