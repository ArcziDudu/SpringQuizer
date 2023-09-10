package com.quizer.api.controller.view;

import com.quizer.business.services.PlayerService;
import com.quizer.business.services.RegisterService;
import com.quizer.domain.dto.RegistrationFormDto;
import com.quizer.infrastructure.entity.PlayerEntity;
import com.quizer.infrastructure.security.UserEntity;
import com.quizer.infrastructure.security.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class LoginRegisterController {
    private final String LOGIN = "/login";
    private final String REGISTER = "/register";
    private final String REGISTER_SAVE = "/register/save";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegisterService registerService;
    private final PlayerService playerService;
    @GetMapping(LOGIN)
    public String login() {
        return "login";
    }
    @GetMapping(REGISTER)
    public String showRegistrationForm(Model model) {
        RegistrationFormDto form = new RegistrationFormDto();
        model.addAttribute("form", form);
        return "register";
    }
    @PostMapping(REGISTER_SAVE)
    public String registration(@Valid
                               @ModelAttribute("form") RegistrationFormDto form,
                               BindingResult result) {

        if (userRepository.existsByEmail(form.getEmail())) {
            result.rejectValue("email", "400", "This email is already registered");
        } else if (userRepository.existsByUserName(form.getUserName())) {
            result.rejectValue("userName", "400", "This username is already registered");
        }

        if (result.hasErrors()) {
            return "register";
        }
        UserEntity newUser = UserEntity.builder()
                .email(form.getEmail())
                .userName(form.getUserName())
                .password(passwordEncoder.encode(form.getPassword()))
                .roles(new HashSet<>())
                .active(true)
                .build();
        PlayerEntity newPlayer = PlayerEntity.builder()
                .userName(newUser.getUserName())
                .userEmail(newUser.getEmail())
                .user(newUser)
                .build();
        registerService.save(newUser);
        playerService.createNewPlayer(newPlayer);
        return "redirect:/register?success=true";

    }
}
