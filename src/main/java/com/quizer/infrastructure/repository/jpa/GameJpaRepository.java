package com.quizer.infrastructure.repository.jpa;

import com.quizer.infrastructure.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameJpaRepository extends JpaRepository<GameEntity, Integer> {
}
