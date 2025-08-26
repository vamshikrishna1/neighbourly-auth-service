package com.neighbourly.auth.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorResponse {
    private String uuid;
    private List<Error> errList;
}
