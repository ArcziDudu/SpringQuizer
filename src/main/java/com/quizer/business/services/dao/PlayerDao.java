package com.quizer.business.services.dao;

import com.quizer.domain.dto.PlayerDto;
import com.quizer.infrastructure.entity.PlayerEntity;

import java.util.List;
import java.util.Optional;

public interface PlayerDao {
    void savePlayer(PlayerEntity player);


    Optional<PlayerEntity> findByUserName(String adminek);

    Optional<PlayerDto> findDtoByUserName(String userName);

    List<PlayerDto> findAllPlayers();

}
