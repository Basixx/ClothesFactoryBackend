package com.kodilla.ClothesFactoryBackend.repository;

import com.kodilla.ClothesFactoryBackend.domain.ShipmentHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ShipmentHistoryRepository extends CrudRepository<ShipmentHistory, Long> {

    @Override
    List<ShipmentHistory> findAll();
}