package com.quizer.infrastructure.repository.jpaImpl;

import com.quizer.business.services.dao.GameDao;
import com.quizer.infrastructure.entity.GameEntity;
import com.quizer.infrastructure.repository.jpa.GameJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class GameJpaImpl implements GameDao {
    private GameJpaRepository gameJpaRepository;
    @Override
    public void addGameToDatabase(GameEntity game) {
        gameJpaRepository.save(game);
    }
}
