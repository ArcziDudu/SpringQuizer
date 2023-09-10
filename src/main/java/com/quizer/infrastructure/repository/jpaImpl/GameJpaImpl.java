package com.quizer.infrastructure.repository.jpaImpl;

import com.quizer.business.services.dao.GameDao;
import com.quizer.domain.dto.GameDto;
import com.quizer.infrastructure.entity.GameEntity;
import com.quizer.infrastructure.repository.jpa.GameJpaRepository;
import com.quizer.infrastructure.repository.mapper.GameMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class GameJpaImpl implements GameDao {
    private GameJpaRepository gameJpaRepository;
    private final GameMapper gameMapper;
    @Override
    public void addGameToDatabase(GameEntity game) {
        gameJpaRepository.save(game);
    }

    @Override
    public List<GameDto> fetchAllGames() {
        return gameJpaRepository.findAll().stream().map(gameMapper::mapFromEntity).toList();
    }
}
