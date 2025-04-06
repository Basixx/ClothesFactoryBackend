package com.clothes.factory.repository;

import com.clothes.factory.domain.ShipmentHistory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentHistoryRepository extends JpaRepository<ShipmentHistory, Long> {

    default List<ShipmentHistory> findAll(int page, int size) {
        return findAll(PageRequest.of(page, size)).getContent();
    }

}
