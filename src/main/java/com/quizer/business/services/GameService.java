package com.quizer.business.services;

import com.quizer.business.services.dao.GameDao;
import com.quizer.domain.dto.GameDto;
import com.quizer.infrastructure.entity.GameEntity;
import com.quizer.infrastructure.repository.mapper.GameMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@AllArgsConstructor
public class GameService {
    private final GameDao gameDao;
    private final GameMapper gameMapper;

    public void saveGameToDb(GameDto game) {
        gameDao.addGameToDatabase( gameMapper.mapFromDto(game));
    }
}
