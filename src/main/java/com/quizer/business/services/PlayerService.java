package com.quizer.business.services;

import com.quizer.business.services.dao.PlayerDao;
import com.quizer.domain.dto.PlayerDto;
import com.quizer.infrastructure.entity.PlayerEntity;
import com.quizer.infrastructure.repository.mapper.PlayerMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class PlayerService {
    private PlayerDao playerDao;

    public void createNewPlayer(PlayerEntity newPlayer) {
        playerDao.savePlayer(newPlayer);
    }

    public PlayerEntity findEntityByUserName(String userName) {
        Optional<PlayerEntity> entityByUserName = playerDao.findByUserName(userName);
        if(entityByUserName.isEmpty()){
            throw  new RuntimeException();
        }return entityByUserName.get();
    }

    public PlayerDto findDtoByUserName(String userName) {
        Optional<PlayerDto> dtoByUserName = playerDao.findDtoByUserName(userName);
        if(dtoByUserName.isEmpty()){
            throw new UsernameNotFoundException("user: "+userName+" not found!");
        }return dtoByUserName.get();
    }

    public List<PlayerDto> findAllPlayers() {
        return playerDao.findAllPlayers();
    }
}
