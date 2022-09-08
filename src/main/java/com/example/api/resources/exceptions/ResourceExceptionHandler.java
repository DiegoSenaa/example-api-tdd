package com.example.api.resources.exceptions;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.api.services.exceptions.DataIntegratyVioletionException;
import com.example.api.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandartError> objectNotFound(ObjectNotFoundException ex, HttpServletRequest request) {

        StandartError error = StandartError.builder().timestamp(LocalDate.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(ex.getMessage())
                .path(request.getRequestURI()).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DataIntegratyVioletionException.class)
    public ResponseEntity<StandartError> dataIntegratyVioletion(DataIntegratyVioletionException ex, HttpServletRequest request) {

        StandartError error = StandartError.builder().timestamp(LocalDate.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(ex.getMessage())
                .path(request.getRequestURI()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
