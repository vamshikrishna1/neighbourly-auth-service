package com.neighbourly.auth.model;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

@Data
public class RegisterRequest {



    @NotBlank(message = "First name is mandatory")
    private String firstName;
    private String lastName;
    @Email(message = "Invalid email address")
    private String email;
    @NotBlank(message = "Password is mandatory")
    private String password;
    private String mobile;
}
