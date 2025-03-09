package com.clothes.factory.repository;

import com.clothes.factory.domain.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    @NonNull
    @Override
    List<Order> findAll();

}
