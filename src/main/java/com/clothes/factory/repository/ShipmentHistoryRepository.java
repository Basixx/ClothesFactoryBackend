package com.clothes.factory.repository;

import com.clothes.factory.domain.ShipmentHistory;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentHistoryRepository extends JpaRepository<ShipmentHistory, Long> {

    @NonNull
    @Override
    Page<ShipmentHistory> findAll(@NonNull Pageable pageable);

}
