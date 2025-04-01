package com.clothes.factory.repository;

import com.clothes.factory.domain.AdminToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminTokenRepository extends JpaRepository<AdminToken, Long> {

    Boolean existsByToken(String token);

}
