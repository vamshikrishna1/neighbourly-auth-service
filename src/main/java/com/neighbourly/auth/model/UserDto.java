package com.neighbourly.auth.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String mobile;
}
