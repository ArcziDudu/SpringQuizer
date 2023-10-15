package com.quizer.domain.dto;

import com.quizer.infrastructure.entity.GameEntity;
import lombok.*;

import java.util.Set;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {
    private Integer playerId;
    private String userName;
    private String userEmail;
    private Set<GameDto> games;
    private Integer amountOfPlayedGames;
    private Integer totalPoints;
}
