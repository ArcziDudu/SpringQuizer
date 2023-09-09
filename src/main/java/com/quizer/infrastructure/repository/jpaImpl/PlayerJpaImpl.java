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
    private PlayerMapper playerMapper;
    @Override
    public void savePlayer(PlayerEntity player) {
        playerJpaRepository.save(player);
    }

    @Override
    public Optional<PlayerDto> findPlayer(String username) {
        return playerJpaRepository.findByUserName(username).map(playerMapper::mapFromEntity);
    }

    public PlayerEntity get(int id){
        return playerJpaRepository.findById(id).get();
    }
}
