package com.quizer.infrastructure.entity;

import com.quizer.infrastructure.security.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "playerId")
@Entity
@Table(name = "players")
public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Integer playerId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_email")
    private String userEmail;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "player")
    private Set<GameEntity> games;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
