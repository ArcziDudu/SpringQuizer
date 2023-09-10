package com.quizer.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationFormDto {

    @NotEmpty(message = "Enter an email address!")
    @Email(message = "Enter a valid email address!")
    private String email;

    @NotEmpty(message = "Enter a username!")
    @NotEmpty(message = "user name cannot be empty.")
    private String userName;

    @Size(min = 6, message = "Password must be at least six characters long.")
    @Pattern(regexp = "^(?=.*[0-9]).{6,}$", message = "Password must contain at least one digit.")
    @NotEmpty(message = "Password cannot be empty.")
    private String password;

}
