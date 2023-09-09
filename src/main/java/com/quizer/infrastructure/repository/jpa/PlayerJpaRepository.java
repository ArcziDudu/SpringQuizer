package com.quizer.infrastructure.repository.jpa;

import com.quizer.infrastructure.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerJpaRepository extends JpaRepository<PlayerEntity, Integer> {

    Optional<PlayerEntity> findByUserName(String userName);
}
