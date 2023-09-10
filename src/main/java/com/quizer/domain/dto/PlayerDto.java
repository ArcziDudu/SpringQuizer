package com.quizer.domain.dto;

import com.quizer.infrastructure.entity.GameEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
