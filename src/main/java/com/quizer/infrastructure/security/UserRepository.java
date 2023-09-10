package com.quizer.infrastructure.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    UserEntity findByUserName(String userName);

}
