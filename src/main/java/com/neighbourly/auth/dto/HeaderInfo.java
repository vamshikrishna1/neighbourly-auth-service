package com.neighbourly.auth.dto;


import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class HeaderInfo {
    private String uuid;
    private String userId;
}
