package com.reportcard.project.exceptions;


import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(
            NotFoundException ex, WebRequest request) {

    	var body = new HashMap<String, String>();
    	body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateItemException.class)
    public ResponseEntity<Object> handleDuplicateItemException(
    		DuplicateItemException ex, WebRequest request) {

    	var body = new HashMap<String, String>();
    	body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers,
        HttpStatus status, WebRequest request) {

      Map<String, String> body = ex.getBindingResult()
          .getFieldErrors()
          .stream()
          .collect(Collectors.toMap(e -> e.getField(), e -> e.getDefaultMessage()));

      return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}