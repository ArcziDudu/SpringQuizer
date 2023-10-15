package com.quizer.business.services;


import com.quizer.infrastructure.security.RoleRepository;
import com.quizer.infrastructure.security.UserEntity;
import com.quizer.infrastructure.security.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class RegisterService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public void save(UserEntity newUser) {
        newUser.getRoles().add(roleRepository.findByRole("PLAYER"));
        newUser.setActive(true);
        userRepository.save(newUser);
    }

}
