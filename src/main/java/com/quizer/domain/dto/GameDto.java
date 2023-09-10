package com.quizer.domain.dto;

import com.quizer.infrastructure.entity.GameEntity;
import com.quizer.infrastructure.entity.PlayerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameDto implements Comparable<GameDto>{
    private Integer gameId;
    private OffsetDateTime dateOfGame;
    private String quizDifficulty;
    private String quizCategory;
    private Integer points;
    private PlayerEntity player;

    @Override
    public int compareTo(GameDto other) {
        return other.getDateOfGame().compareTo(this.getDateOfGame());
    }
}
