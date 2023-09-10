package com.quizer.infrastructure.repository.jpaImpl;

import com.quizer.business.services.dao.PlayerDao;
import com.quizer.domain.dto.PlayerDto;
import com.quizer.infrastructure.entity.PlayerEntity;
import com.quizer.infrastructure.repository.jpa.PlayerJpaRepository;
import com.quizer.infrastructure.repository.mapper.PlayerMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class PlayerJpaImpl implements PlayerDao {
    private PlayerJpaRepository playerJpaRepository;
    private final PlayerMapper playerMapper;
    @Override
    public void savePlayer(PlayerEntity player) {
        playerJpaRepository.save(player);
    }

    @Override
    public Optional<PlayerEntity> findByUserName(String userName) {
        return playerJpaRepository.findByUserName(userName);
    }

    @Override
    public Optional<PlayerDto> findDtoByUserName(String userName) {
        return playerJpaRepository.findByUserName(userName).map(playerMapper::mapFromEntity);
    }


}
