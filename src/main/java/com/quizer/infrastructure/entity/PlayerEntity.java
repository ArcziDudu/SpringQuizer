package com.quizer.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"playerId", "userEmail"})
@Entity
@Table(name = "players")
public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Integer playerId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    private String userEmail;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "player")
    private Set<GameEntity> games;

}
