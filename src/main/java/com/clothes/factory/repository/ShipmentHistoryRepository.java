package com.clothes.factory.repository;

import com.clothes.factory.domain.ShipmentHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentHistoryRepository extends CrudRepository<ShipmentHistory, Long> {

    @NonNull
    @Override
    List<ShipmentHistory> findAll();

}
