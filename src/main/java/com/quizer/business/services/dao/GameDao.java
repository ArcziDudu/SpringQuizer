package com.quizer.business.services.dao;

import com.quizer.domain.dto.GameDto;
import com.quizer.infrastructure.entity.GameEntity;

import java.util.List;

public interface GameDao {
    void addGameToDatabase(GameEntity game);

    List<GameDto> fetchAllGames();
}
