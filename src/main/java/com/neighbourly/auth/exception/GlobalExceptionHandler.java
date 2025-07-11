package com.neighbourly.auth.exception;

import com.neighbourly.auth.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.neighbourly.auth.model.Error;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex){

        List<Error> errors = ex.getBindingResult().getFieldErrors().stream().map(fieldError -> new Error(fieldError.getField(), fieldError.getDefaultMessage())).toList();
        ErrorResponse errorResponse = ErrorResponse.builder().errList(errors).build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException exception){
        Error error = new Error(exception.getErrorCode(), exception.getErrorDescription());
        ErrorResponse errorResponse = ErrorResponse.builder().errList(List.of(error)).build();
        return ResponseEntity.badRequest().body(errorResponse);
    }


}
