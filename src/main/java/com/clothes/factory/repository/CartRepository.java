package com.clothes.factory.repository;

import com.clothes.factory.domain.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {

    @NonNull
    @Override
    List<Cart> findAll();

}
