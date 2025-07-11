package com.neighbourly.auth.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class BadRequestException  extends  RuntimeException{
    private String errorCode;
    private String errorDescription;
    private String uuid;

}
