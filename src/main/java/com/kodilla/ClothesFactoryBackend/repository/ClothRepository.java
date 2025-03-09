package com.kodilla.ClothesFactoryBackend.repository;

import com.kodilla.ClothesFactoryBackend.domain.Cloth;
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
