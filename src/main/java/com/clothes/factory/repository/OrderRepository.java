package com.clothes.factory.repository;

import com.clothes.factory.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    default List<Order> findAll(int page, int size) {
        return findAll(PageRequest.of(page, size)).getContent();
    }

    default List<Order> findAllByUserId(Long userId,
                                        int page,
                                        int size) {
        return findByUserId(userId, PageRequest.of(page, size)).getContent();
    }

    Page<Order> findByUserId(Long userId, Pageable pageable);

}
