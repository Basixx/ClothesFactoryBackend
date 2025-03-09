package com.clothes.factory.repository;

import com.clothes.factory.domain.Cloth;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClothRepository extends CrudRepository<Cloth, Long> {

    @NonNull
    @Override
    List<Cloth> findAll();

}
