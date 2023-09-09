package com.quizer.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.quizer.infrastructure.constants.QuizDifficulty;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "gameId")
@Entity
@Table(name = "games")
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Integer gameId;

    @Column(name = "date_of_game")
    private OffsetDateTime dateOfGame;

    @Column(name = "quizDifficulty")
    private String quizDifficulty;

    @Column(name = "quizCategory")
    private String quizCategory;
    @Column(name = "points")
    private Integer points;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    private PlayerEntity player;

}
