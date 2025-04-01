package com.clothes.factory.repository;

import com.clothes.factory.domain.SignInHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignInHistoryRepository extends JpaRepository<SignInHistory, Long> {

}
