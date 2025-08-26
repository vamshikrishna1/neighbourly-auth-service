package com.neighbourly.auth.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoleDto {
    private Long id;
    private String roleName;
    private String description;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
}
