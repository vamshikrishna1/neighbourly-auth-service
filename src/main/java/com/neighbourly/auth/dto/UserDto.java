package com.neighbourly.auth.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class UserDto {
    private Long userId;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    private String password;
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private List<Long> communityId;
    private boolean active;
    private Set<RoleDto> roles=new HashSet<>();
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
}
