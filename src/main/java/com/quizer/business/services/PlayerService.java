package com.quizer.business.services;

import com.quizer.business.services.dao.PlayerDao;
import com.quizer.domain.dto.PlayerDto;
import com.quizer.infrastructure.entity.PlayerEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PlayerService {
    private PlayerDao playerDao;

    public void savePlayer(PlayerEntity player){
        playerDao.savePlayer(player);
    }
    public PlayerDto findPlayer(String userName){
        return playerDao.findPlayer(userName).get();

    }
}
