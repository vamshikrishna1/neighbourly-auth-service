package com.neighbourly.auth.exception;

import com.neighbourly.auth.dto.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.neighbourly.auth.dto.Error;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<List<Error>>> handleValidationException(MethodArgumentNotValidException ex){

        List<Error> errors = ex.getBindingResult().getFieldErrors().stream().map(fieldError -> new Error(fieldError.getField(), fieldError.getDefaultMessage())).toList();
        Response<List<Error>> errorResponse = Response.<List<Error>>builder().data(errors).build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Response<List<Error>>> handleBadRequestException(BadRequestException exception){
        Error error = new Error(exception.getErrorCode(), exception.getErrorDescription());
        Response<List<Error>> errorResponse = Response.<List<Error>>builder().data(List.of(error)).build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Response<List<Error>>> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        Error error = new Error("400", "Missing required header: " + e.getHeaderName());
        Response<List<Error>> response = Response.<List<Error>>builder().data(List.of(error)).build();
        return ResponseEntity.badRequest().body(response);
    }


}
